package com.esprit.jobfinder.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_quiz")
public class UserQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private int totalScore;
}
