package com.esprit.jobfinder.services;

import java.util.List;
import java.util.Optional;

import com.esprit.jobfinder.models.Offer;

import reactor.core.publisher.Flux;

public interface OfferService {
    Offer createOffer(Offer offer) throws Exception;
    Optional<Offer> updateOffer(int id, Offer offerDetails);
    void deleteOffer(int id);
    Flux<Offer> getAllOffers();
    Optional<Offer> getOfferById(int id);
}