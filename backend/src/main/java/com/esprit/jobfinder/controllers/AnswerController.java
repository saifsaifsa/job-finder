package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Answer;
import com.esprit.jobfinder.services.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/answers")
@Validated
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping
    public List<Answer> getAllAnswers() {
        return answerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
        Answer answer = answerService.findById(id);
        if (answer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(answer);
    }

    @PostMapping
    public Answer createAnswer(@Valid @RequestBody Answer answer) {
        return answerService.save(answer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @Valid @RequestBody Answer answerDetails) {
        Answer answer = answerService.findById(id);
        if (answer == null) {
            return ResponseEntity.notFound().build();
        }
        answer.setContent(answerDetails.getContent());
        answer.setCorrect(answerDetails.isCorrect());
        answer.setScore(answerDetails.getScore());
        return ResponseEntity.ok(answerService.save(answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        Answer answer = answerService.findById(id);
        if (answer == null) {
            return ResponseEntity.notFound().build();
        }
        answerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
