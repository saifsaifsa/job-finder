package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.UserQuiz;
import com.esprit.jobfinder.repository.UserQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQuizService {

    @Autowired
    private UserQuizRepository userQuizRepository;

    public UserQuiz save(UserQuiz userQuiz) {
        return userQuizRepository.save(userQuiz);
    }

    public UserQuiz findById(Long id) {
        Optional<UserQuiz> userQuiz = userQuizRepository.findById(id);
        return userQuiz.orElse(null);
    }
}
