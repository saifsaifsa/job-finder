package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.esprit.jobfinder.repository.ITrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.data.domain.Sort;

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
    @Override
    public List<Training> getAllOrderByPrice(String direction) {
        if(direction.equals("asc"))
            return trainingRepository.findAll(Sort.by(Sort.Direction.ASC,"price"));
        else
            return trainingRepository.findAll(Sort.by(Sort.Direction.DESC,"price"));
    }
    @Override
    public List<Training> getAllOrderByLikes(String direction) {
        if(direction.equals("asc"))
            return trainingRepository.findAll(Sort.by(Sort.Direction.ASC,"likes"));
        else
            return trainingRepository.findAll(Sort.by(Sort.Direction.DESC,"likes"));
    }

    @Override
    public Page<Training> getAllTrainings(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return trainingRepository.findAll(pageable);
    }
    @Override
    public Training likeTraining(long id){
        Training training = getTraining(id);
        training.setLikes(training.getLikes() + 1 );
        training.setRating(rating(training.getLikes(),training.getDislikes()));
        return updateTraining(training);
    }
    @Override
    public Training dislikeTraining(long id){
        Training training = getTraining(id);
        training.setDislikes(training.getDislikes() + 1 );
        training.setRating(rating(training.getLikes(),training.getDislikes()));
        return updateTraining(training);
    }

    private int rating(int like,int dislike){
        return like * 100 / (like + dislike);
    }

}
