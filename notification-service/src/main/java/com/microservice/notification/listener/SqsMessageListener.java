package com.microservice.notification.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.notification.model.NotificationEvent;
import com.microservice.notification.model.SnsMessage;
import com.microservice.notification.processor.NotificationProcessor;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageListener {

    private final ObjectMapper objectMapper;
    private final NotificationProcessor notificationProcessor;

    public SqsMessageListener(ObjectMapper objectMapper, NotificationProcessor notificationProcessor) {
        this.objectMapper = objectMapper;
        this.notificationProcessor = notificationProcessor;
    }

    @SqsListener("${spring.cloud.aws.sqs.queue-name}")
    public void recieveMessage(String message) {
        try {
            SnsMessage snsMessage = objectMapper.readValue(message, SnsMessage.class);

            Object actualMessage = snsMessage.getMessage();

            NotificationEvent notificationEvent;

            if (actualMessage instanceof String) {
                // Case 1: stringified JSON
                notificationEvent = objectMapper.readValue((String) actualMessage, NotificationEvent.class);
            } else {
                // Case 2: already JSON object
                notificationEvent = objectMapper.convertValue(actualMessage, NotificationEvent.class);
            }

            System.out.println("parsedEvent " + notificationEvent.getEventType());
            notificationProcessor.process(notificationEvent);

        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

}

