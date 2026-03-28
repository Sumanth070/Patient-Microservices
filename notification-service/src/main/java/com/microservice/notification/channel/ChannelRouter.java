package com.microservice.notification.channel;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.endpoints.internal.Value;

@Component
public class ChannelRouter {

    private final EmailChannel emailChannel;
    private final SmsChannel smsChannel;

    public ChannelRouter(EmailChannel emailChannel, SmsChannel smsChannel) {
        this.emailChannel = emailChannel;
        this.smsChannel = smsChannel;
    }

    public void route(String message, String channel) {

        if (channel == null) {
            System.out.println("Channel is null, defaulting to EMAIL");
            emailChannel.send(message);
            return;
        }

        channel = channel.trim(); // 🔥 CRITICAL FIX

        System.out.println("Normalized channel: [" + channel + "]");

        if ("EMAIL".equalsIgnoreCase(channel)) {
            emailChannel.send(message);

        } else if ("SMS".equalsIgnoreCase(channel)) {
            smsChannel.send(message);

        } else if ("BOTH".equalsIgnoreCase(channel)) {
            emailChannel.send(message);
            smsChannel.send(message);

        } else {
            System.out.println("Unknown channel, defaulting to EMAIL: " + channel);
            emailChannel.send(message);
        }
    }
}
