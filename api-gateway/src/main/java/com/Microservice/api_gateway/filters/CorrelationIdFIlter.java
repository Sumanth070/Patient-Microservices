package com.Microservice.api_gateway.filters;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.UUID;

@Component
public class CorrelationIdFIlter implements GlobalFilter {

    private static final String HEADER_NAME = "X-Correlation-ID";
    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFIlter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders().getFirst(HEADER_NAME);
        if(correlationId == null){
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put("correlationId",correlationId);
        log.info("Correlation filter executed. ID={}",correlationId);
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(exchange.getRequest().mutate().header(HEADER_NAME,correlationId).build())
                .build();
        return chain.filter(mutatedExchange).doFinally(signal ->MDC.remove("correlationId"));

    }
}
