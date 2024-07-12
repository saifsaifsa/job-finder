package com.esprit.jobfinder.services;

import com.esprit.jobfinder.dto.TrainingDTO;
import com.esprit.jobfinder.dto.TrainingMapper;
import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.esprit.jobfinder.repository.ITrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ITrainingServiceImpl implements ITrainingService{
    private final ITrainingRepository trainingRepository;
    @Autowired
    private IFileUploaderService fileUploaderService;

    @Override
    public Training addTraining(Training training, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String filePath;
            try {
                filePath = fileUploaderService.uploadFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            training.setImage(filePath);
        }
        return trainingRepository.save(training);
    }

    @Override
    public Training addTrain(TrainingDTO train, MultipartFile image){

        Training training = TrainingMapper.toEntity(train);
        if (image != null && !image.isEmpty()) {
            String filePath;
            try {
                filePath = fileUploaderService.uploadFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            training.setImage(filePath);
        }
        return trainingRepository.save(training);
    }
    @Override
    public Training updateTrain(TrainingDTO train, MultipartFile image){
        Training training = TrainingMapper.toEntity(train);
        Assert.notNull(train.getId(),"Training Id must not be null");
        training.setId(train.getId());
        if (image != null && !image.isEmpty()) {
            String filePath;
            try {
                filePath = fileUploaderService.uploadFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            training.setImage(filePath);
        }
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
public Training updateTraining(Training training, MultipartFile image) {
        Assert.notNull(training.getId(),"Training Id must not be null");

        if (image != null && !image.isEmpty()) {
            String filePath;
            try {
                filePath = fileUploaderService.uploadFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            training.setImage(filePath);
        }

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
        return updateTraining(training,null);
    }
    @Override
    public Training dislikeTraining(long id){
        Training training = getTraining(id);
        training.setDislikes(training.getDislikes() + 1 );
        training.setRating(rating(training.getLikes(),training.getDislikes()));
        return updateTraining(training,null);
    }

    private int rating(int like,int dislike){
        return like * 100 / (like + dislike);
    }

    public Map<String, Object> getTrainingStatistics() {
        long totalTrainings = trainingRepository.count();
        List<Training> mostDislikedTrainings = trainingRepository.findMostDislikedTrainings(PageRequest.of(0, 5)); // Get top 5 most disliked trainings
        List<Training> mostLikedTrainings = trainingRepository.findMostLikedTrainings(PageRequest.of(0, 5)); // Get top 5 most disliked trainings
        List<Object[]> mostPopularCategories = trainingRepository.findMostPopularCategories();
        List<Object[]> averagePricePerCategory = trainingRepository.findAveragePricePerCategory();
        List<Training> upcomingTrainings = trainingRepository.findUpcomingTrainings( PageRequest.of(0, 5)); // Get top 5 upcoming trainings
        List<Training> recentlyFinishedTrainings = trainingRepository.findRecentlyFinishedTrainings( PageRequest.of(0, 5)); // Get top 5 recently finished trainings
        List<Object[]> durationOfTrainings = trainingRepository.findDurationOfTrainings();
        List<Object[]> numberOfTrainingsPerCategory = trainingRepository.findNumberOfTrainingsPerCategory();
        List<Object[]> averageDurationPerCategory = trainingRepository.findAverageDurationPerCategory();
        List<Object[]> monthlyEngagementTrends = trainingRepository.findMonthlyEngagementTrends();
        List<Object[]> ratingTrendsOverTime = trainingRepository.findRatingTrendsOverTime();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTrainings", totalTrainings);
        stats.put("mostLikedTrainings", mostLikedTrainings);
        stats.put("mostDislikedTrainings", mostDislikedTrainings);
        stats.put("mostPopularCategories", mostPopularCategories);
        stats.put("averagePricePerCategory", averagePricePerCategory);
        stats.put("upcomingTrainings", upcomingTrainings);
        stats.put("recentlyFinishedTrainings", recentlyFinishedTrainings);
        stats.put("durationOfTrainings", durationOfTrainings);
        stats.put("numberOfTrainingsPerCategory", numberOfTrainingsPerCategory);
        stats.put("averageDurationPerCategory", averageDurationPerCategory);
        stats.put("monthlyEngagementTrends", monthlyEngagementTrends);
        stats.put("ratingTrendsOverTime", ratingTrendsOverTime);

        return stats;
    }
}
