package com.esprit.jobfinder.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;


    @NotNull
    private String description;


    @NotNull
    private String industry;


    @NotNull
    private String location;

    private Double rating;

    private String image;

    @JsonIgnore
    @ManyToMany(mappedBy = "companies",fetch = FetchType.LAZY)
    Set<User> subscribers;


    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Offer> offers;






}

