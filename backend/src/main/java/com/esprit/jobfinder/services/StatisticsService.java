package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Participation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    public double calculateAverageScore(List<Participation> participations) {
        return participations.stream()
                .mapToDouble(Participation::getScore)
                .average()
                .orElse(0);
    }

}
