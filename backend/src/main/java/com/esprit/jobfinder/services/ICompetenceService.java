package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Competence;
import java.util.List;
import java.util.Set;

public interface ICompetenceService {
    List<Competence> findAll();
    Competence findById(Long id);
    Competence save(Competence competence);
    void deleteById(Long id);
    List<Competence> findByCategory(String category);
    void addQuizToCompetence(Long competenceId, Long quizId);
    Competence getCompetenceWithQuizzes(Long competenceId);
    List<Competence> findByids(Set<Long> ids);
}
