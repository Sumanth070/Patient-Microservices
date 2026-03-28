package com.microservices.appointment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackages = "com.microservices.appointment.controller")
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponse> handleValid(MethodArgumentNotValidException ex){
        log.warn("patient not found exception",ex.getMessage());
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                "VALIDATION_ERROR",
                "Invalid Request Payload",
                HttpStatus.CONFLICT.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        @ExceptionHandler(PatientNotFoundException.class)
        public ResponseEntity<ValidationErrorResponse> handlePatientNotFound(PatientNotFoundException ex) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        }
        @ExceptionHandler(AppointmentNotFoundException.class)
        public ResponseEntity<ValidationErrorResponse> handleAppointmentNotFound(AppointmentNotFoundException ex) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(InvalidAppointmentTimeException.class)
        public String handleInvalidTime(InvalidAppointmentTimeException ex) {
            return ex.getMessage();
        }

        @ExceptionHandler(PatientServiceUnavailableException.class)
        public ResponseEntity<ValidationErrorResponse> handlePatientServiceUnavailable(PatientServiceUnavailableException ex) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationErrorResponse> handleGeneral(Exception ex){
        log.error("unhandles exception",ex);
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
