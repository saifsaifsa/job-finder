package com.esprit.jobfinder.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.esprit.jobfinder.exceptions.BadRequestException;
import com.esprit.jobfinder.models.Offer;
import com.esprit.jobfinder.repository.OfferRepository;


@Service
public class OfferServiceImpl implements OfferService {

    
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
        }

        @Override
        public Offer createOffer(Offer offer) {
        try {
            offer.validate();
            offer.setCreationDate(new Date());
            
            offer.setStatus("open"); 
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
        offerRepository.delete(offer);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
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