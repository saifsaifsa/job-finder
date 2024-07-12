package com.esprit.jobfinder.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du quiz est obligatoire")
    private String title;

    private double totalScore;

    @Positive(message = "Le seuil de réussite doit être une valeur positive")
    private double successScore;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @ManyToOne
    private Competence competence;

//    @NotBlank(message = "Le chemin de l'image est obligatoire")
//    private String imagePath;
}