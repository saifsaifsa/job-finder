package com.esprit.jobfinder.services;

import com.esprit.jobfinder.dto.QuizDTO;
import com.esprit.jobfinder.dto.QuizMapper;
import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.models.Answer;
import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Question;
import com.esprit.jobfinder.models.Quiz;
import com.esprit.jobfinder.repository.CompetenceRepository;
import com.esprit.jobfinder.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private CompetenceRepository competenceRepository;

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Quiz findById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    @Transactional
    public Quiz save(QuizDTO quizDTO) {
        Optional<Competence> competenceOpt = competenceRepository.findById(quizDTO.getCompetenceId());
        if (competenceOpt.isEmpty()) {
            throw new NotFoundException("Competence not found");
        }

        Quiz quiz = QuizMapper.toEntity(quizDTO, competenceOpt.get());
        quiz.setTotalScore(0);

        List<Question> questions = new ArrayList<>();
        for (Question questionDTO : quizDTO.getQuestions()) {
            Question question = new Question();
            question.setContent(questionDTO.getContent());

            List<Answer> answers = new ArrayList<>();
            for (Answer answerDTO : questionDTO.getAnswers()) {
                Answer answer = new Answer();
                answer.setContent(answerDTO.getContent());
                answer.setCorrect(answerDTO.isCorrect());
                answer.setScore(answerDTO.getScore());
                answer.setQuestion(question); // Set the question for the answer
                answers.add(answer);
            }

            question.setAnswers(answers); // Set answers for the question
            question.setQuiz(quiz); // Set the quiz for the question
            questions.add(question);
        }

        quiz.setQuestions(questions); // Set questions for the quiz
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, @NotNull Quiz quizDetails) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setTitle(quizDetails.getTitle());
        quiz.setTotalScore(quizDetails.getTotalScore());
        quiz.setSuccessScore(quizDetails.getSuccessScore());
        quiz.setQuestions(quizDetails.getQuestions());
        return quizRepository.save(quiz);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
    public List<Quiz> getQuizzesByCompetenceId(Long competenceId){
        return this.quizRepository.findQuizzesByCompetenceId(competenceId);
    }
}

