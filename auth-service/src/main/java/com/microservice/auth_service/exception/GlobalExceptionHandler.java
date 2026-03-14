package com.microservice.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreayExistException.class)
    public ResponseEntity<ValidationErrorResponse> handleDuplicateEmail(Exception ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(),ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNameTakenException.class)
    public ResponseEntity<ValidationErrorResponse> handleDuplicateUsername(Exception ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValid(MethodArgumentNotValidException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "VALIDATION_ERROR",
                "Invalid Request Payload");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AlgorithmNotAvailableException.class)
    public ResponseEntity<ValidationErrorResponse> handleAlgorithmNotAvailable(AlgorithmNotAvailableException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }


}
