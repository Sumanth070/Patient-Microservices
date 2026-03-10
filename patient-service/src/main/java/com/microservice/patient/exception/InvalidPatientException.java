package com.microservice.patient.exception;

public class InvalidPatientException extends RuntimeException{
    public InvalidPatientException(String message){
        super(message);
    }

}
