package com.microservices.appointment.event.test;

import com.microservices.appointment.event.model.AppointmentCreatedEvent;
import com.microservices.appointment.event.model.EventType;
import com.microservices.appointment.event.publisher.AppointmentEventPublisher;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventTestRunner {

    private final AppointmentEventPublisher publisher;

    public EventTestRunner(AppointmentEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void testEvent() {

        AppointmentCreatedEvent event = new AppointmentCreatedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventType(EventType.APPOINTMENT_CREATED);
        event.setVersion("v1");
        event.setTimestamp(LocalDateTime.now());
        event.setSource("appointment-service");

        AppointmentCreatedEvent.DataPayload data =
                new AppointmentCreatedEvent.DataPayload(
                        "1",
                        "101",
                        "501",
                        LocalDateTime.now()
                );

        event.setPayload(data);

        publisher.publishAppointmentCreatedEvent(event);

        System.out.println("🔥 TEST EVENT SENT TO SNS");
    }
}