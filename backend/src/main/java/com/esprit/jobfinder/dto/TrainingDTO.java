package com.esprit.jobfinder.dto;

import com.esprit.jobfinder.models.enums.TrainingCategories;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
public class TrainingDTO {
        private long id;
        private String title;
        private String description;
        private double price;
        @Enumerated(EnumType.STRING)
        private TrainingCategories trainingCategories;
        @Temporal(TemporalType.DATE)
        private LocalDate dateDebut;
        @Temporal(TemporalType.DATE)
        private LocalDate dateFin;
        private String image;


}
