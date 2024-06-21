package com.yourapp.controllers;

import com.yourapp.models.Quiz;
import com.yourapp.services.QuizService;
import com.yourapp.utils.PDFExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quiz);
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.save(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quizDetails) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        quiz.setTitle(quizDetails.getTitle());
        quiz.setDuration(quizDetails.getDuration());
        quiz.setAttempts(quizDetails.getAttempts());
        quiz.setPassThreshold(quizDetails.getPassThreshold());
        return ResponseEntity.ok(quizService.save(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        quizService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportQuizzesToPDF() {
        List<Quiz> quizzes = quizService.findAll();
        byte[] pdfContent = PDFExporter.exportQuizzesToPDF(quizzes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "quizzes.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}


