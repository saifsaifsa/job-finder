package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT q FROM Quiz q WHERE q.competence.id = ?1")
    List<Quiz> findQuizzesByCompetenceId(Long competenceId);
}
