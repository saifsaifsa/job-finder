package com.esprit.jobfinder.payload.request;

import com.esprit.jobfinder.models.enums.TrainingCategories;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateTrainingReq {
    private MultipartFile image;

    @Enumerated(EnumType.STRING)
    private TrainingCategories trainingCategories;

    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "id cannot be blank")
    private String id;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "price cannot be blank")
    private String price;
    private String rating;
    private String likes;
    private String dislikes;
    private String  dateDebut;
    private String dateFin;
}
