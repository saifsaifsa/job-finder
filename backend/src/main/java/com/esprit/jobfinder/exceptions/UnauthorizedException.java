package com.esprit.jobfinder.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super ( message );
    }
}
