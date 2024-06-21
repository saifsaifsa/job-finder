package com.yourapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Positive(message = "Duration must be positive")
    private int duration;

    private int attempts;
    private double passThreshold;

    // Getters and Setters
}
