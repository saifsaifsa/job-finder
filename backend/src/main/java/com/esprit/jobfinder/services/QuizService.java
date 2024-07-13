package com.esprit.jobfinder.services;

import com.esprit.jobfinder.dto.QuizDTO;
import com.esprit.jobfinder.dto.QuizMapper;
import com.esprit.jobfinder.exceptions.NotFoundException;
import com.esprit.jobfinder.models.Answer;
import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Question;
import com.esprit.jobfinder.models.Quiz;
import com.esprit.jobfinder.repository.AnswerRepository;
import com.esprit.jobfinder.repository.CompetenceRepository;
import com.esprit.jobfinder.repository.QuestionRepository;
import com.esprit.jobfinder.repository.QuizRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

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
            questionRepository.save(question);
            List<Answer> answers = new ArrayList<>();
            for (Answer answerDTO : questionDTO.getAnswers()) {
                Answer answer = new Answer();
                answer.setContent(answerDTO.getContent());
                answer.setCorrect(answerDTO.isCorrect());
                answer.setScore(answerDTO.getScore());
                answer.setQuestion(question);
                answers.add(answer);
                answerRepository.saveAll(answers);
            }
            question.setAnswers(answers);
            question.setQuiz(quiz);
            questions.add(question);
        }
        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    @Transactional
    public Quiz updateQuiz(Long id, @Valid QuizDTO quizDTO) {
        Quiz existingQuiz = quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found"));

        existingQuiz.setTitle(quizDTO.getTitle());
        existingQuiz.setSuccessScore(quizDTO.getSuccessScore());

        List<Question> existingQuestions = existingQuiz.getQuestions();
        List<Question> newQuestions = quizDTO.getQuestions();

        for (Question questionDTO : newQuestions) {
            Question question;
            if (questionDTO.getId() != null) {
                question = existingQuestions.stream().filter(q -> q.getId().equals(questionDTO.getId())).findFirst().orElseThrow(() -> new NotFoundException("Question not found"));
                question.setContent(questionDTO.getContent());

                // Update answers
                List<Answer> existingAnswers = question.getAnswers();
                List<Answer> newAnswers = questionDTO.getAnswers();

                for (Answer answerDTO : newAnswers) {
                    Answer answer;
                    if (answerDTO.getId() != null) {

                        answer = existingAnswers.stream().filter(a -> a.getId().equals(answerDTO.getId())).findFirst().orElseThrow(() -> new NotFoundException("Answer not found"));
                        answer.setContent(answerDTO.getContent());
                        answer.setCorrect(answerDTO.isCorrect());
                        answer.setScore(answerDTO.getScore());
                    } else {
                        // Add new answer
                        answer = new Answer();
                        answer.setContent(answerDTO.getContent());
                        answer.setCorrect(answerDTO.isCorrect());
                        answer.setScore(answerDTO.getScore());
                        answer.setQuestion(question);
                        existingAnswers.add(answer);
                    }
                }

                // Remove deleted answers
                existingAnswers.removeIf(answer -> newAnswers.stream().noneMatch(dto -> dto.getId().equals(answer.getId())));

            } else {
                // Add new question
                question = new Question();
                question.setContent(questionDTO.getContent());
                question.setQuiz(existingQuiz);

                List<Answer> answers = new ArrayList<>();
                for (Answer answerDTO : questionDTO.getAnswers()) {
                    Answer answer = new Answer();
                    answer.setContent(answerDTO.getContent());
                    answer.setCorrect(answerDTO.isCorrect());
                    answer.setScore(answerDTO.getScore());
                    answer.setQuestion(question);
                    answers.add(answer);
                }
                question.setAnswers(answers);
                existingQuestions.add(question);
            }
        }

        existingQuestions.removeIf(question -> newQuestions.stream().noneMatch(dto -> dto.getId().equals(question.getId())));

        return quizRepository.save(existingQuiz);
    }


    public void deleteById(Long id) {
        this.quizRepository.deleteById(id);
    }
    public List<Quiz> getQuizzesByCompetenceId(Long competenceId){
        return this.quizRepository.findQuizzesByCompetenceId(competenceId);
    }
}

