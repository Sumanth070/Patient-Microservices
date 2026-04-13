package com.microservice.notification.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class NotificationEvent {

    @JsonProperty("eventId")
    private String  eventId;
    @JsonProperty("eventType")
    private String eventType;
    @JsonProperty("source")
    private String source;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("payload")
    private Map<String,Object> payload;

    public NotificationEvent() {
    }

    public NotificationEvent(String eventId, String eventType, String source, String timestamp, String channel, Map<String, Object> payload) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.source = source;
        this.timestamp = timestamp;
        this.channel = channel;
        this.payload = payload;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Map<String, Object> getData() {
        return payload;
    }

    public void setData(Map<String, Object> payload) {
        this.payload = payload;
    }
}
