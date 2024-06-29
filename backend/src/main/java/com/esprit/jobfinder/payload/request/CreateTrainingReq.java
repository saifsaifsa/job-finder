package com.esprit.jobfinder.payload.request;

import com.esprit.jobfinder.models.enums.TrainingCategories;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class CreateTrainingReq {

    private MultipartFile image;

    @Enumerated(EnumType.STRING)
    private TrainingCategories trainingCategories;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "lastName cannot be blank")
    private String price;
    private String rating;
    private String likes;
    private String dislikes;
    private String  dateDebut;
    private String dateFin;
}