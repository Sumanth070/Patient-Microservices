package com.microservice.patient.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationErrorResponse> handleGeneral(Exception ex){
        log.error("unhandles exception",ex);
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Something went wrong"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValid(MethodArgumentNotValidException ex){
        log.warn("Patient not found exception: {}", ex.getMessage());
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "VALIDATION_ERROR",
                "Invalid Request Payload");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ValidationErrorResponse> handleDuplicateEmail(DuplicateEmailException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPatientException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidPatient(InvalidPatientException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientDeletionException.class)
    public ResponseEntity<ValidationErrorResponse> handlePatientDeletion(PatientDeletionException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);

    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ValidationErrorResponse> handlePatientNotFound(PatientNotFoundException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
