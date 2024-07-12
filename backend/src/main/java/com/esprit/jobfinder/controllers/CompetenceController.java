package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.services.CompetenceService;
import com.esprit.jobfinder.utiles.PDFExporter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competences")
@Validated
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;

    @GetMapping
    public List<Competence> getAllCompetences() {
        return competenceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competence> getCompetenceById(@PathVariable Long id) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(competence);
    }

    @PostMapping
    public Competence createCompetence(@Valid @RequestBody Competence competence) {
        return competenceService.save(competence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competence> updateCompetence(@PathVariable Long id, @Valid @RequestBody Competence competenceDetails) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        competence.setName(competenceDetails.getName());
        competence.setCategory(competenceDetails.getCategory());
        return ResponseEntity.ok(competenceService.save(competence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        competenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public List<Competence> getCompetencesByCategory(@PathVariable String category) {
        return competenceService.findByCategory(category);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCompetencesToPDF() {
        List<Competence> competences = competenceService.findAll();
        byte[] pdfContent = PDFExporter.exportCompetencesToPDF(competences);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "competences.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }

    @GetMapping("/{competenceId}")
    public Competence getCompetenceWithQuizzes(@PathVariable Long competenceId) {
        return competenceService.getCompetenceWithQuizzes(competenceId);
    }
}

