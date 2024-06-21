package com.esprit.jobfinder.payload.response.httpErrorResponses;

import org.springframework.http.HttpStatus;

public class ConflictErrorResponse extends HttpErrorResponse {

    public ConflictErrorResponse(String message, String description) {
        super(message, description);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
