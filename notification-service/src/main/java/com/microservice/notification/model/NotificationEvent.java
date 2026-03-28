package com.microservice.notification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.services.sqs.endpoints.internal.Value;

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

    private Map<String,Object> data;

    public NotificationEvent() {
    }

    public NotificationEvent(String eventId, String eventType, String source, String timestamp, String channel, Map<String, Object> data) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.source = source;
        this.timestamp = timestamp;
        this.channel = channel;
        this.data = data;
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
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
