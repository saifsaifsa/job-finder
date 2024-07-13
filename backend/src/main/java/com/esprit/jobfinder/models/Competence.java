package com.esprit.jobfinder.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

    @JsonIgnore
    @OneToMany(mappedBy = "competence", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Quiz> quizzes;

    @JsonIgnore

    @ManyToMany(mappedBy = "competences")
    private Collection<Offer> offers;

}
