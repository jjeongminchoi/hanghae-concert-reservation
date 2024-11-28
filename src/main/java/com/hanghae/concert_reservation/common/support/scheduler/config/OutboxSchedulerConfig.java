package com.hanghae.concert_reservation.common.support.scheduler.config;

import com.hanghae.concert_reservation.domain.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class OutboxSchedulerConfig {

    private final OutboxService outboxService;

    @Scheduled(fixedRate = 10000)
    public void processOutboxEvents() {
        outboxService.publishScheduleEvents();
    }
}
