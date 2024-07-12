package com.esprit.jobfinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esprit.jobfinder.models.LoyaltyPoints;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.repository.LoyaltyPointsRepository;

@Service
public class LoyaltyPointsService {

    @Autowired
    private LoyaltyPointsRepository loyaltyPointsRepository;

    public void addPoints(User user, int points) {
        LoyaltyPoints loyaltyPoints = loyaltyPointsRepository.findByUser(user)
                .orElse(new LoyaltyPoints());
        loyaltyPoints.setUser(user);
        loyaltyPoints.setPoints(loyaltyPoints.getPoints() + points);
        loyaltyPointsRepository.save(loyaltyPoints);
    }

    public int getPoints(User user) {
        return loyaltyPointsRepository.findByUser(user)
                .map(LoyaltyPoints::getPoints)
                .orElse(0);
    }

    public void resetPoints(User user) {
        LoyaltyPoints loyaltyPoints = loyaltyPointsRepository.findByUser(user)
                .orElse(new LoyaltyPoints());
        loyaltyPoints.setUser(user);
        loyaltyPoints.setPoints(0);
        loyaltyPointsRepository.save(loyaltyPoints);
    }
}