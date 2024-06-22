package com.esprit.jobfinder.services;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.repository.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepository;

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
}
