package com.yourapp.controllers;

import com.yourapp.models.Participation;
import com.yourapp.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
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
    public Participation createParticipation(@RequestBody Participation participation) {
        return participationService.save(participation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Participation> updateParticipation(@PathVariable Long id, @RequestBody Participation participationDetails) {
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
    public List<Participation> getParticipationsByUserId(@PathVariable Long userId) {
        return participationService.findByUserId(userId);
    }

    @GetMapping("/quiz/{quizId}")
    public List<Participation> getParticipationsByQuizId(@PathVariable Long quizId) {
        return participationService.findByQuizId(quizId);
    }

    @GetMapping("/{id}/score")
    public ResponseEntity<Double> getScore(@PathVariable Long id) {
        double score = participationService.calculateScore(id);
        return ResponseEntity.ok(score);
    }
}
