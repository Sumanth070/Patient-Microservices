package com.microservices.appointment.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCorrelationConfig {
    private static final String HEADER_NAME = "X-Correlation-ID";

    @Bean
    public RequestInterceptor correlationInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String correlationId = MDC.get("correlationId");
                if(correlationId!=null){
                    requestTemplate.header(HEADER_NAME,correlationId);
                }
            }
        };
    }
}
