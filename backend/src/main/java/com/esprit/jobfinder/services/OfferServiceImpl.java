package com.esprit.jobfinder.services;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.esprit.jobfinder.exceptions.BadRequestException;
import com.esprit.jobfinder.models.Company;
import com.esprit.jobfinder.models.Offer;
import com.esprit.jobfinder.repository.OfferRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    @Transactional
    @Cacheable(value = "offres")
    public Offer createOffer(Offer offer) {
        try {
            offer.validate();
            offer.setCreationDate(new Date());

            offer.setStatus("open");
            Company managedCompany = entityManager.merge(offer.getCompany());
            offer.setCompany(managedCompany);
            return offerRepository.save(offer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Failed to create offer");
        }
    }

    @Override
    public Optional<Offer> updateOffer(int id, Offer offerDetails) {
        return offerRepository.findById(id).map(offer -> {
            offer.setTitle(offerDetails.getTitle());
            offer.setDescription(offerDetails.getDescription());
            offer.setType(offerDetails.getType());
            offer.setExperienceLevel(offerDetails.getExperienceLevel());

            return offerRepository.save(offer);
        });
    }

    @Override
    public void deleteOffer(int id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Offer not found with id " + id));
        offerRepository.deleteById(id);
        }
        @Override
        public Flux<Offer> getAllOffers() {
            return Flux.defer(() -> Flux.fromIterable(offerRepository.findAll()))
                    .subscribeOn(Schedulers.boundedElastic())
                    .take(100) // Limit to 100 requests
                    .delayElements(Duration.ofMillis(10)); //  Introduce a delay between emissions
        }

        @Override
        public Optional<Offer> getOfferById(int id) {
        return offerRepository.findById(id);
    }

    @Scheduled(cron = "0 0 0 * * ?") // this runs the method every day at midnight
    public void closeOffers() {
        Date thirtyDaysAgo = new Date(System.currentTimeMillis() - 30L * 24L * 60L * 60L * 1000L);
        List<Offer> offers = offerRepository.findAllByCreationDateBeforeAndStatusNot(thirtyDaysAgo, "closed");
        for (Offer offer : offers) {
            offer.setStatus("closed");
            offerRepository.save(offer);
        }
    }
}