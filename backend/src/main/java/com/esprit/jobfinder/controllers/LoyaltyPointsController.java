package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.services.LoyaltyPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loyalty-points")
public class LoyaltyPointsController {

    @Autowired
    private LoyaltyPointsService loyaltyPointsService;

    @PostMapping("/add")
    public void addPoints(@RequestParam Long userId, @RequestParam int points) {
        User user = new User(); // Assume you have a way to get the user by ID
        user.setId(userId);
        loyaltyPointsService.addPoints(user, points);
    }

    @GetMapping("/get")
    public int getPoints(@RequestParam Long userId) {
        User user = new User(); // Assume you have a way to get the user by ID
        user.setId(userId);
        return loyaltyPointsService.getPoints(user);
    }

    @PostMapping("/reset")
    public void resetPoints(@RequestParam Long userId) {
        User user = new User(); // Assume you have a way to get the user by ID
        user.setId(userId);
        loyaltyPointsService.resetPoints(user);
    }
}