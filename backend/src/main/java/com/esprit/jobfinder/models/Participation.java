package com.yourapp.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long quizId;
    private Date startTime;
    private Date endTime;
    private double score;

    // Getters and Setters
}
