package com.esprit.jobfinder.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du quiz est obligatoire")
    private String title;

    @Positive(message = "Le score total doit être une valeur positive")
    private double totalScore;

    @Positive(message = "Le seuil de réussite doit être une valeur positive")
    private double successScore;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    // Getters and Setters

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
