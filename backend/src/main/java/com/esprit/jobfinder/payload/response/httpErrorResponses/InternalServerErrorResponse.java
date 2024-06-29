package com.esprit.jobfinder.payload.response.httpErrorResponses;

import org.springframework.http.HttpStatus;

public class InternalServerErrorResponse extends HttpErrorResponse {

    public InternalServerErrorResponse(String message, String description) {
        super(message, description);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
