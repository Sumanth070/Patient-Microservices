package com.microservice.notification.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.endpoints.internal.Value;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class IdempotencyService {

    private final Set<String> processedEvents= ConcurrentHashMap.newKeySet();

    public boolean isDuplicate(String eventId){
        return processedEvents.contains(eventId);
    }
    public void markProcessed(String eventId) {
        processedEvents.add(eventId);
    }

}
