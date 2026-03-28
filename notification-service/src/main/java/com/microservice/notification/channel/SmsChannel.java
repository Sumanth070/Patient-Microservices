package com.microservice.notification.channel;

import org.springframework.stereotype.Component;

@Component
public class SmsChannel implements NotificationChannel {
    @Override
    public void send(String message) {
            System.out.println("📱 Sending SMS: " + message);
    }
}
