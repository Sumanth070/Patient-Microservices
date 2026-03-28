package com.microservices.appointment.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIDFilter extends OncePerRequestFilter {

    private final static String HEADER_NAME = "X-Correlation-ID";
    private static final Logger log = LoggerFactory.getLogger(CorrelationIDFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String correlationId = request.getHeader(HEADER_NAME);

        if(correlationId==null || correlationId.isEmpty()){
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put("correlationId",correlationId);
        log.info("Appointment filter correlationId={}", correlationId);

        try {
            filterChain.doFilter(request,response);
        }
        finally {
            MDC.remove("correlationId");
        }
    }
}
