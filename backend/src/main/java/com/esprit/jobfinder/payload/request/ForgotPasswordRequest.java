package com.esprit.jobfinder.payload.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
}
