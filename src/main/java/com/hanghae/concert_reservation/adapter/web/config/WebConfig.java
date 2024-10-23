package com.hanghae.concert_reservation.adapter.web.config;

import com.hanghae.concert_reservation.adapter.web.interceptor.WaitingQueueInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final WaitingQueueInterceptor waitingQueueInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(waitingQueueInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/users/**");
    }
}
