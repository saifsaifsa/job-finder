package com.esprit.jobfinder.repository;


import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ITrainingRepository extends JpaRepository<Training,Long> {
    Set<Training> findTrainingByTrainingCategories(TrainingCategories trainingCategories);
    @Query("SELECT t FROM Training t ORDER BY t.likes DESC")
    List<Training> findMostLikedTrainings(Pageable pageable);

    @Query("SELECT t FROM Training t ORDER BY t.dislikes DESC")
    List<Training> findMostDislikedTrainings(Pageable pageable);

    @Query("SELECT t.trainingCategories, SUM(t.likes) as totalLikes FROM Training t GROUP BY t.trainingCategories ORDER BY totalLikes DESC")
    List<Object[]> findMostPopularCategories();

    @Query("SELECT t.title, AVG(t.rating) as avgRating FROM Training t GROUP BY t.title ORDER BY avgRating DESC")
    List<Object[]> findAverageRatingPerTraining();


    @Query("SELECT t.trainingCategories, AVG(t.price) as avgPrice FROM Training t GROUP BY t.trainingCategories ORDER BY avgPrice DESC")
    List<Object[]> findAveragePricePerCategory();

    @Query("SELECT t FROM Training t WHERE t.dateDebut >= CURRENT_DATE ORDER BY t.dateDebut ASC")
    List<Training> findUpcomingTrainings(Pageable pageable);

    @Query("SELECT t FROM Training t WHERE t.dateFin <= CURRENT_DATE ORDER BY t.dateFin DESC")
    List<Training> findRecentlyFinishedTrainings(Pageable pageable);

    @Query("SELECT t.title, DATEDIFF(t.dateFin, t.dateDebut) as duration FROM Training t ORDER BY duration DESC")
    List<Object[]> findDurationOfTrainings();

    @Query("SELECT t.trainingCategories, COUNT(t) as trainingCount FROM Training t GROUP BY t.trainingCategories ORDER BY trainingCount DESC")
    List<Object[]> findNumberOfTrainingsPerCategory();

    @Query("SELECT t.trainingCategories, AVG(DATEDIFF(t.dateFin, t.dateDebut)) as avgDuration FROM Training t GROUP BY t.trainingCategories ORDER BY avgDuration DESC")
    List<Object[]> findAverageDurationPerCategory();


    @Query("SELECT MONTH(t.dateDebut), SUM(t.likes) as monthlyLikes, SUM(t.dislikes) as monthlyDislikes FROM Training t GROUP BY MONTH(t.dateDebut) ORDER BY MONTH(t.dateDebut)")
    List<Object[]> findMonthlyEngagementTrends();

    @Query("SELECT t.dateDebut, AVG(t.rating) as avgRating FROM Training t GROUP BY t.dateDebut ORDER BY t.dateDebut")
    List<Object[]> findRatingTrendsOverTime();

}
