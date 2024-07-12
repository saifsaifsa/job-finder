package com.esprit.jobfinder.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LoyaltyPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int points;
}

