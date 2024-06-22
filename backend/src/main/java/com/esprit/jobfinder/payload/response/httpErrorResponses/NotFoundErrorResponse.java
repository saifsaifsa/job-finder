package com.esprit.jobfinder.payload.response.httpErrorResponses;
import org.springframework.http.HttpStatus;

public class NotFoundErrorResponse extends HttpErrorResponse {

    public NotFoundErrorResponse(String message, String description) {
        super(message, description);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

