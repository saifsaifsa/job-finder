package com.yourapp.services;

import com.yourapp.models.Participation;
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
