package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByUserId(Long userId);
    List<Participation> findByQuizId(Long quizId);
}
