package com.esprit.jobfinder.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super ( message );
    }
}
