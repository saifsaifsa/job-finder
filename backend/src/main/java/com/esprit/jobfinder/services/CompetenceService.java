package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Quiz;
import com.esprit.jobfinder.repository.CompetenceRepository;
import com.esprit.jobfinder.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private QuizRepository quizRepository;
    public List<Competence> findAll() {
        return competenceRepository.findAll();
    }

    public Competence findById(Long id) {
        return competenceRepository.findById(id).orElse(null);
    }

    public Competence save(Competence competence) {
        return competenceRepository.save(competence);
    }

    public void deleteById(Long id) {
        competenceRepository.deleteById(id);
    }

    public List<Competence> findByCategory(String category) {
        return competenceRepository.findByCategory(category);
    }

    @Transactional
    public void addQuizToCompetence(Long competenceId, Long quizId) {
        Optional<Competence> competenceOptional = competenceRepository.findById(competenceId);
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);

        if (competenceOptional.isPresent() && quizOptional.isPresent()) {
            Competence competence = competenceOptional.get();
            Quiz quiz = quizOptional.get();
            competence.getQuizzes().add(quiz);
            competenceRepository.save(competence);
        }
    }

    public Competence getCompetenceWithQuizzes(Long competenceId) {
        return competenceRepository.findById(competenceId).orElse(null);
    }
}