package com.esprit.jobfinder.payload.response.httpErrorResponses;

import org.springframework.http.HttpStatus;

public class ForbiddenErrorResponse extends HttpErrorResponse {

    public ForbiddenErrorResponse(String message, String description) {
        super(message, description);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
