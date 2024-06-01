package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;

import java.util.List;
import java.util.Set;

public interface ITrainingService {
    public Training addTraining(Training training);
    public List<Training> getAll();
    public Training getTraining(long id);
    public Training updateTraining(Training training);
    public void DeleteTraining(long id);
    public Set<Training> findTrainingByCategories(TrainingCategories trainingCategories);
}
