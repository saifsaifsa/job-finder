package com.esprit.jobfinder.dto;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Quiz;
import com.esprit.jobfinder.models.Training;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public static TrainingDTO toDto(Training training) {
        if (training == null) {
            return null;
        }

        TrainingDTO dto = new TrainingDTO();
        dto.setTitle(training.getTitle());
        dto.setDescription(training.getDescription());
        dto.setPrice(training.getPrice());
        dto.setTrainingCategories(training.getTrainingCategories());
        dto.setDateDebut(training.getDateDebut());
        dto.setDateFin(training.getDateFin());
        return dto;
    }
    public static Training toEntity(TrainingDTO training) {
        if (training == null) {
            return null;
        }
        Training dto = new Training();
        dto.setTitle(training.getTitle());
        dto.setDescription(training.getDescription());
        dto.setPrice(training.getPrice());
        dto.setTrainingCategories(training.getTrainingCategories());
        dto.setDateDebut(training.getDateDebut());
        dto.setDateFin(training.getDateFin());
        return dto;
    }
}
