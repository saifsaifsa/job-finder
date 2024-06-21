package com.esprit.jobfinder.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super ( message );
    }
}
