package com.microservice.patient.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestTracingFilter extends OncePerRequestFilter {
    private static final String TRACE_ID = "traceid";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException{
        String traceid = UUID.randomUUID().toString();
        MDC.put(TRACE_ID,traceid);
        try {
            filterChain.doFilter(request, response);
        }
        finally {
            MDC.remove(TRACE_ID);
        }
    }
}
