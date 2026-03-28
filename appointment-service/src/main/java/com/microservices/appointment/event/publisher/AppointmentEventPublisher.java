package com.microservices.appointment.event.publisher;

import com.microservices.appointment.event.config.SnsProperties;
import com.microservices.appointment.event.model.AppointmentCreatedEvent;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEventPublisher {

    private final SnsTemplate snsTemplate;
    private final SnsProperties snsProperties;

    public AppointmentEventPublisher(SnsTemplate snsTemplate, SnsProperties snsProperties) {
        this.snsTemplate = snsTemplate;
        this.snsProperties = snsProperties;
    }

    public void publishAppointmentCreatedEvent(AppointmentCreatedEvent event){
        snsTemplate.convertAndSend(snsProperties.getTopicArn(),event);
    }

}
