package com.esprit.jobfinder.repositories;

import com.esprit.jobfinder.models.LoyaltyPoints;
import com.esprit.jobfinder.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {
    Optional<LoyaltyPoints> findByUser(User user);
}