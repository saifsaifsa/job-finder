package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Participation;
import com.esprit.jobfinder.services.ParticipationService;
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
@RequestMapping("/api/participations")
@Validated
public class ParticipationController {
    @Autowired
    private ParticipationService participationService;

    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable Long id) {
        Participation participation = participationService.findById(id);
        if (participation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(participation);
    }

    @PostMapping
    public Participation createParticipation(@Valid @RequestBody Participation participation) {
        return participationService.save(participation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participation> updateParticipation(@PathVariable Long id, @Valid @RequestBody Participation participationDetails) {
        Participation participation = participationService.findById(id);
        if (participation == null) {
            return ResponseEntity.notFound().build();
        }
        participation.setUserId(participationDetails.getUserId());
        participation.setQuizId(participationDetails.getQuizId());
        participation.setStartTime(participationDetails.getStartTime());
        participation.setEndTime(participationDetails.getEndTime());
        participation.setScore(participationDetails.getScore());
        return ResponseEntity.ok(participationService.save(participation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id) {
        Participation participation = participationService.findById(id);
        if (participation == null) {
            return ResponseEntity.notFound().build();
        }
        participationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public List<Participation> getParticipationsByUser(@PathVariable Long userId) {
        return participationService.findByUserId(userId);
    }

    @GetMapping("/quiz/{quizId}")
    public List<Participation> getParticipationsByQuiz(@PathVariable Long quizId) {
        return participationService.findByQuizId(quizId);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportResultsToPDF() {
        List<Participation> participations = participationService.findAll();
        byte[] pdfContent = PDFExporter.exportResultsToPDF(participations);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "results.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
