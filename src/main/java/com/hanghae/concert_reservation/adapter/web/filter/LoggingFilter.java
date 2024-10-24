package com.hanghae.concert_reservation.adapter.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        log.info("Request URI: {}", request.getMethod() + " " + request.getRequestURI());
        log.info("Request WaitingQueueUuid: {}, Contents: {}", request.getHeader("WaitingQueueUuid"), request.getContentAsString());

        filterChain.doFilter(servletRequest, servletResponse);

        log.info("Response Status: {}, Body: {}", response.getStatus(), new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
    }
}
