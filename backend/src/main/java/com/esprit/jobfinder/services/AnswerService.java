package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Answer;
import com.esprit.jobfinder.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }
}
