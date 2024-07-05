package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Answer;
import com.esprit.jobfinder.models.Question;
import com.esprit.jobfinder.models.Quiz;
import com.esprit.jobfinder.services.AnswerService;
import com.esprit.jobfinder.services.QuestionService;
import com.esprit.jobfinder.services.QuizService;
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
@RequestMapping("/api/quizzes")
@Validated
public class QuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

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
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.save(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @Valid @RequestBody Quiz quizDetails) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        quiz.setTitle(quizDetails.getTitle());
        quiz.setTotalScore(quizDetails.getTotalScore());
        quiz.setSuccessScore(quizDetails.getSuccessScore());
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

    @GetMapping("/{id}/questions")
    public List<Question> getQuestionsByQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return null;
        }
        return quiz.getQuestions();
    }

    @PostMapping("/{quizId}/questions")
    public Question addQuestionToQuiz(@PathVariable Long quizId, @Valid @RequestBody Question question) {
        Quiz quiz = quizService.findById(quizId);
        if (quiz != null) {
            question.setQuiz(quiz);
            return questionService.save(question);
        }
        return null;
    }

    @DeleteMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestionFromQuiz(@PathVariable Long quizId, @PathVariable Long questionId) {
        Question question = questionService.findById(questionId);
        if (question != null && question.getQuiz().getId().equals(quizId)) {
            questionService.deleteById(questionId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{questionId}/answers")
    public Answer addAnswerToQuestion(@PathVariable Long questionId, @Valid @RequestBody Answer answer) {
        Question question = questionService.findById(questionId);
        if (question != null) {
            answer.setQuestion(question);
            return answerService.save(answer);
        }
        return null;
    }

    @DeleteMapping("/{questionId}/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswerFromQuestion(@PathVariable Long questionId, @PathVariable Long answerId) {
        Answer answer = answerService.findById(answerId);
        if (answer != null && answer.getQuestion().getId().equals(questionId)) {
            answerService.deleteById(answerId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
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


