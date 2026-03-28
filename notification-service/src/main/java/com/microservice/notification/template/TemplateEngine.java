package com.microservice.notification.template;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.endpoints.internal.Value;

import java.util.Map;

@Component
public class TemplateEngine {

    public String generateMessage(String template, Map<String,Object> data){

        String message = template;

        for(Map.Entry<String,Object> entry: data.entrySet()){
            String placeholder = "{" + entry.getKey() +"}";
            message = message.replace(placeholder,String.valueOf(entry.getValue()));
        }
        return message;
    }

}
