package com.microservice.notification.processor;

import com.microservice.notification.channel.ChannelRouter;
import com.microservice.notification.model.NotificationEvent;
import com.microservice.notification.service.IdempotencyService;
import com.microservice.notification.service.TemplateService;
import com.microservice.notification.template.TemplateEngine;
import org.springframework.stereotype.Component;

@Component
public class NotificationProcessor {

    private final TemplateEngine templateEngine;
    private final ChannelRouter channelRouter;
    private final IdempotencyService idempotencyService;
    private final TemplateService templateService;

    public NotificationProcessor(TemplateEngine templateEngine, ChannelRouter channelRouter, IdempotencyService idempotencyService, TemplateService templateService) {
        this.templateEngine = templateEngine;
        this.channelRouter = channelRouter;
        this.idempotencyService = idempotencyService;
        this.templateService = templateService;
    }

    public void process(NotificationEvent notificationEvent){

        String eventId = notificationEvent.getEventId();

        // 🔥 Step 1: Check duplicate
        if (idempotencyService.isDuplicate(eventId)) {
            System.out.println("Duplicate event detected, skipping: " + eventId);
            return;
        }

        try {
            if ("APPOINTMENT_CREATED".equals(notificationEvent.getEventType())) {
                handleAppointmentCreated(notificationEvent);
            }

            // 🔥 Step 2: Mark as processed ONLY after success
            idempotencyService.markProcessed(eventId);

        } catch (Exception e) {
            throw new RuntimeException(e); // retry
        }
    }

    private void handleUnknown(NotificationEvent event) {
        System.out.println("Unknown event: " + event.getEventType());
    }

    private void handleAppointmentCreated(NotificationEvent notificationEvent) {
        String template = templateService.getTemplate(notificationEvent.getEventType());
        String message = templateEngine.generateMessage(template, notificationEvent.getData());


        System.out.println("Generated Message: " + message);
        channelRouter.route(message, notificationEvent.getChannel());
    }
}

