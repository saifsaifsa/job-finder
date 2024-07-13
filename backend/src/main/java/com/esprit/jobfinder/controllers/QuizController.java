package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.dto.QuizDTO;
import com.esprit.jobfinder.models.*;
import com.esprit.jobfinder.payload.request.SubmitQuizReq;
import com.esprit.jobfinder.repository.QuizRepository;
import com.esprit.jobfinder.services.*;
import com.esprit.jobfinder.utiles.PDFExporter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private UserQuizService userQuizService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizRepository quizRepository;

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

    @PostMapping("/skills/{competenceId}")
    public Quiz createQuiz(@Valid @RequestBody QuizDTO quiz, @PathVariable Long competenceId) {
        quiz.setCompetenceId(competenceId);
        return quizService.save(quiz);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @Valid @RequestBody QuizDTO quizDTO) {
        Quiz updatedQuiz = quizService.updateQuiz(id, quizDTO);
        return ResponseEntity.ok(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        }
        quizRepository.delete(quiz);
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
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<Integer> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody SubmitQuizReq submitQuizReq) {
        Quiz quiz = quizService.findById(quizId);
        Optional<User> user = userService.getUserById(submitQuizReq.getUserId());

        if (quiz == null || !user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        int totalScore = 0;
        for (Answer answer : submitQuizReq.getAnswers()) {
            Answer correctAnswer = answerService.findById(answer.getId());
            if (correctAnswer != null && correctAnswer.isCorrect() && correctAnswer.getQuestion().getQuiz().getId().equals(quizId)) {
                totalScore += correctAnswer.getScore();
            }
        }

        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setUser(user.get());
        userQuiz.setQuiz(quiz);
        userQuiz.setTotalScore(totalScore);
        userQuiz.setSuccess( quiz.getSuccessScore() <= totalScore);
        userQuizService.save(userQuiz);

        return ResponseEntity.ok(totalScore);
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<List<Quiz>> getQuizBySkillsId(@PathVariable Long id) {
        Competence competence = competenceService.findById(id);
        if (competence == null) {
            return ResponseEntity.notFound().build();
        }
        List<Quiz> quizzes = this.quizService.getQuizzesByCompetenceId(id);
        return ResponseEntity.ok(competence.getQuizzes());
    }
}