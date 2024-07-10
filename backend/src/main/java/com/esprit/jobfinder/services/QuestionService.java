package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Question;
import com.esprit.jobfinder.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}

