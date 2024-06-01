package com.esprit.jobfinder.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esprit.jobfinder.exceptions.ResourceNotFoundException;
import com.esprit.jobfinder.models.Offer;
import com.esprit.jobfinder.services.OfferService;

// add cross origin *
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Offer createdOffer = offerService.createOffer(offer);
        return ResponseEntity.ok(createdOffer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable int id, @RequestBody Offer offerDetails) {
        return offerService.updateOffer(id, offerDetails)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable int id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable int id) {
        return offerService.getOfferById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id " + id));
    }

    @PostMapping("/matchCV")
    public ResponseEntity<String> matchCV() {
        return ResponseEntity.ok("CV matching .....");
    }

    @PostMapping("/sendJobAlerts")
    public ResponseEntity<String> sendJobAlerts() {
        
        return ResponseEntity.ok("Job alerts sending  .....");
    }
}
