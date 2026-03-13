package com.microservice.auth_service.exception;

public class EmailAlreayExistException extends RuntimeException {
    public EmailAlreayExistException(String message){
        super(message);
    }
}
