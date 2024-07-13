package com.esprit.jobfinder.payload.request;

import com.esprit.jobfinder.models.Answer;
import lombok.Data;

import java.util.List;

@Data
public class SubmitQuizReq {
    private List<Answer> answers;
    private Long userId;
}
