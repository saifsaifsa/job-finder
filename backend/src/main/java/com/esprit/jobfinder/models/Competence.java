package com.esprit.jobfinder.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la compétence est obligatoire")
    private String name;

    @NotBlank(message = "La catégorie de la compétence est obligatoire")
    private String category;
    @ManyToMany
    @JoinTable(
            name = "competence_quiz",
            joinColumns = @JoinColumn(name = "competence_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private Set<Quiz> quizzes = new HashSet<>();
}

