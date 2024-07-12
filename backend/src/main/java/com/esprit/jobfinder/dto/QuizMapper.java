package com.esprit.jobfinder.dto;


import com.esprit.jobfinder.models.Competence;
import com.esprit.jobfinder.models.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public static QuizDTO toDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizDTO dto = new QuizDTO();
        dto.setTitle(quiz.getTitle());
        dto.setSuccessScore(quiz.getSuccessScore());
        dto.setQuestions(quiz.getQuestions());
        dto.setCompetenceId(quiz.getCompetence() != null ? quiz.getCompetence().getId() : null);

        return dto;
    }

    public static Quiz toEntity(QuizDTO dto, Competence competence) {
        if (dto == null) {
            return null;
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setSuccessScore(dto.getSuccessScore());
        quiz.setQuestions(dto.getQuestions());
        quiz.setCompetence(competence);

        return quiz;
    }
}
