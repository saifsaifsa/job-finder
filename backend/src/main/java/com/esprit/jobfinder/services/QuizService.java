package com.yourapp.services;

import com.yourapp.models.Quiz;
import com.yourapp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
