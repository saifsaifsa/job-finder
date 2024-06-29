package com.esprit.jobfinder.models;

import java.util.Date;
import java.util.List;

import com.esprit.jobfinder.models.enums.ExperienceLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String type;
    private Date creationDate;
    private String status;
    private int nombreVu;
    private String experience;
    private String niveauEtude;
    private String tags;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

}
