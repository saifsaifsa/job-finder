package com.esprit.jobfinder.payload.response.httpErrorResponses;

public class HttpErrorResponse {
    private String message;
    private String description;

    public HttpErrorResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }

    // Getters and Setters (optional)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

