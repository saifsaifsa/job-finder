package com.esprit.jobfinder.payload.response.httpErrorResponses;

import org.springframework.http.HttpStatus;

public class UnauthorizedErrorResponse extends HttpErrorResponse {

    public UnauthorizedErrorResponse(String message, String description) {
        super(message, description);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}