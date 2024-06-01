package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.esprit.jobfinder.repository.ITrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ITrainingServiceImpl implements ITrainingService{
    private final ITrainingRepository trainingRepository;

    @Override
    public Training addTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public List<Training> getAll() {
        return trainingRepository.findAll();
    }

    @Override
    public Training getTraining(long id) {
        return trainingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No training found with this Id"));
    }

    @Override
    public Training updateTraining(Training training) {
        Assert.notNull(training.getId(),"Training Id must not be null");
        return trainingRepository.save(training);
    }

    @Override
    public void DeleteTraining(long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public Set<Training> findTrainingByCategories(TrainingCategories trainingCategories) {
        return trainingRepository.findTrainingByTrainingCategories(trainingCategories);
    }
}
