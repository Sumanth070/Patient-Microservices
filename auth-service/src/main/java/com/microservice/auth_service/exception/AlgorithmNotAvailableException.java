package com.microservice.auth_service.exception;

public class AlgorithmNotAvailableException extends RuntimeException{
    public AlgorithmNotAvailableException(String message){
        super(message);
    }
}
