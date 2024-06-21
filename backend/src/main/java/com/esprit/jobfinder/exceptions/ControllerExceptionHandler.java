package com.esprit.jobfinder.exceptions;

import java.util.Date;

import com.esprit.jobfinder.payload.response.httpErrorResponses.*;
import com.esprit.jobfinder.payload.response.httpErrorResponses.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }

  
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ErrorMessage> nullPointerExceptionHandler(NullPointerException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BadRequestErrorResponse> handleBadRequestException(BadRequestException ex) {
    BadRequestErrorResponse errorResponse = new BadRequestErrorResponse("Invalid body", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<UnauthorizedErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
    UnauthorizedErrorResponse errorResponse = new UnauthorizedErrorResponse("Unauthorized", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ForbiddenErrorResponse> handleForbiddenException(ForbiddenException ex) {
    ForbiddenErrorResponse errorResponse = new ForbiddenErrorResponse("Forbidden", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<NotFoundErrorResponse> handleNotFoundException(NotFoundException ex) {
    NotFoundErrorResponse errorResponse = new NotFoundErrorResponse("Not Found", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<InternalServerErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
    InternalServerErrorResponse errorResponse = new InternalServerErrorResponse("Internal Server Error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ConflictErrorResponse> handleConflictException(ConflictException ex) {
    ConflictErrorResponse errorResponse = new ConflictErrorResponse("Conflict", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BadRequestErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    FieldError fieldError = ex.getBindingResult().getFieldError();
    String errorMessage = fieldError.getDefaultMessage();

    BadRequestErrorResponse errorResponse = new BadRequestErrorResponse("Invalid body", errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}