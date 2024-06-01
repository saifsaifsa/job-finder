package com.esprit.jobfinder.models;


import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String title;
    private String description;
    private double rating;
    private double price;
    private int likes;
    private int dislikes;
    @Enumerated(EnumType.STRING)
    private TrainingCategories trainingCategories;
    @Temporal(TemporalType.DATE)
    private LocalDate dateDebut;
    @Temporal(TemporalType.DATE)
    private LocalDate dateFin;
    @JsonIgnore
    @ManyToMany(mappedBy = "trainings",fetch = FetchType.LAZY)
    Set<User> subscribers;
}
