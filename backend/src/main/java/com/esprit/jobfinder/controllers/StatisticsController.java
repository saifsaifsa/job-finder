package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Participation;
import com.esprit.jobfinder.services.ParticipationService;
import com.esprit.jobfinder.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ParticipationService participationService;

    @GetMapping("/quiz/{quizId}/average-score")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long quizId) {
        List<Participation> participations = participationService.findByQuizId(quizId);
        double averageScore = statisticsService.calculateAverageScore(participations);
        return ResponseEntity.ok(averageScore);
    }


}
