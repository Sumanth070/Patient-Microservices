package com.microservices.appointment.event.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.sns")
public class SnsProperties {
    private String topicArn;

    public SnsProperties() {
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getTopicArn() {
        return topicArn;
    }
}
