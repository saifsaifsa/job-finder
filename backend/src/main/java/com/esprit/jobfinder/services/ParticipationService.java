package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Participation;
import com.esprit.jobfinder.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationService {
    @Autowired
    private ParticipationRepository participationRepository;

    public List<Participation> findAll() {
        return participationRepository.findAll();
    }

    public Participation findById(Long id) {
        return participationRepository.findById(id).orElse(null);
    }

    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    public void deleteById(Long id) {
        participationRepository.deleteById(id);
    }

    public List<Participation> findByUserId(Long userId) {
        return participationRepository.findByUserId(userId);
    }

    public List<Participation> findByQuizId(Long quizId) {
        return participationRepository.findByQuizId(quizId);
    }

    public double calculateScore(Long participationId) {
        // Logic for calculating score
        Participation participation = participationRepository.findById(participationId).orElse(null);
        if (participation != null) {
            // Assuming you have logic to calculate the score
            return participation.getScore();
        }
        return 0;
    }
}
