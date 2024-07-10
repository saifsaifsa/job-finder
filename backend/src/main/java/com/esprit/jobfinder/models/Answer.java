package com.esprit.jobfinder.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le contenu de la réponse est obligatoire")
    private String content;

    private boolean isCorrect;

    @PositiveOrZero(message = "Le score doit être une valeur positive ou zéro")
    private double score;

    @ManyToOne
    private Question question;
}

