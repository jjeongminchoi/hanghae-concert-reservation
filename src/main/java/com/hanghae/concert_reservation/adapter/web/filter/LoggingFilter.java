package com.hanghae.concert_reservation.adapter.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        log.info("Request API: {}", requestWrapper.getMethod() + " " + requestWrapper.getRequestURI());
        log.info("Request WaitingQueueUuid: {}", requestWrapper.getHeader("WaitingQueueUuid"));

        filterChain.doFilter(servletRequest, servletResponse);

        log.info("Response Status: {}", responseWrapper.getStatus());
    }
}
