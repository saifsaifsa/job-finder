package com.esprit.jobfinder.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
        return offerRepository.save(offer);
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
}