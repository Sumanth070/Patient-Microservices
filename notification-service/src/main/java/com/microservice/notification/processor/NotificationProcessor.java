package com.microservice.notification.processor;

import com.microservice.notification.channel.ChannelRouter;
import com.microservice.notification.model.NotificationEvent;
import com.microservice.notification.service.IdempotencyService;
import com.microservice.notification.template.TemplateEngine;
import org.springframework.stereotype.Component;

@Component
public class NotificationProcessor {

    private final TemplateEngine templateEngine;
    private final ChannelRouter channelRouter;
    private final IdempotencyService idempotencyService;

    public NotificationProcessor(TemplateEngine templateEngine, ChannelRouter channelRouter, IdempotencyService idempotencyService) {
        this.templateEngine = templateEngine;
        this.channelRouter = channelRouter;
        this.idempotencyService = idempotencyService;
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
        String template = "Hello {patientName}, your appointment with {doctorName} is scheduled at {time}";

        String message = templateEngine.generateMessage(template, notificationEvent.getData());

        System.out.println("Generated Message: " + message);
        System.out.println("RAW CHANNEL VALUE: [" + notificationEvent.getChannel() + "]");
        channelRouter.route(message, notificationEvent.getChannel());
    }
}

