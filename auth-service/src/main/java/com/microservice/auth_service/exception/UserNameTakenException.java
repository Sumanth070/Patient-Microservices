package com.microservice.auth_service.exception;

public class UserNameTakenException extends RuntimeException{
    public UserNameTakenException(String message){
        super(message);
    }
}
