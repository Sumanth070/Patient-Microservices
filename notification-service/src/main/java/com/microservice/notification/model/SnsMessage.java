package com.microservice.notification.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SnsMessage {

    @JsonProperty("Type")
    private String type;
    @JsonProperty("Message")
    private Object message;

    public SnsMessage(String type, Object message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
