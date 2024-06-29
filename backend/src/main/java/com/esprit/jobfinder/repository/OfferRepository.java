package com.esprit.jobfinder.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esprit.jobfinder.models.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    List<Offer> findAllByCreationDateBeforeAndStatusNot(Date date, String status);
}