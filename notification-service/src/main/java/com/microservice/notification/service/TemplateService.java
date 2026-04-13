package com.microservice.notification.service;

import com.microservice.notification.repository.TemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;


    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public String getTemplate(String eventType){
        return templateRepository.findByTemplateKey(eventType)
                .orElseThrow(()-> new RuntimeException( "Template not found for eventType=" + eventType))
                .getBody();
    }
}
