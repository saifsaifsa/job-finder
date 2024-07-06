package com.esprit.jobfinder.repository;


import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ITrainingRepository extends JpaRepository<Training,Long> {
    Set<Training> findTrainingByTrainingCategories(TrainingCategories trainingCategories);
}
