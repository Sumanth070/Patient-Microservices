package com.microservice.patient.exception;

import java.time.LocalDateTime;

public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private String Errorcode;
    private String message;
    private int status;

    public ValidationErrorResponse(LocalDateTime timestamp, int status, String errorcode, String message) {
        this.timestamp = timestamp;
        Errorcode = errorcode;
        this.message = message;
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getErrorcode() {
        return Errorcode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
