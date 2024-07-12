package com.esprit.jobfinder.repository;

import com.esprit.jobfinder.models.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
}