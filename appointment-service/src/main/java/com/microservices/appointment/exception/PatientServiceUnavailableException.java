package com.microservices.appointment.exception;

public class PatientServiceUnavailableException extends RuntimeException{
    public PatientServiceUnavailableException(String message){
        super(message);
    }
}
