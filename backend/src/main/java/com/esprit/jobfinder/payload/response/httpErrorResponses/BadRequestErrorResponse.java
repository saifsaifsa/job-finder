package com.esprit.jobfinder.payload.response.httpErrorResponses;

import org.springframework.http.HttpStatus;

public class BadRequestErrorResponse extends HttpErrorResponse {

    public BadRequestErrorResponse(String message, String description) {
        super(message, description);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
