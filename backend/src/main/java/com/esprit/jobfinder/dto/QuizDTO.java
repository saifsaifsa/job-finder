package com.esprit.jobfinder.dto;

import com.esprit.jobfinder.models.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    @NotBlank(message = "Le titre du quiz est obligatoire")
    private String title;

    @Positive(message = "Le seuil de réussite doit être une valeur positive")
    private double successScore;

    private List<Question> questions;

    private Long competenceId;
}
