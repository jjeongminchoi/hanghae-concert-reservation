package com.hanghae.concert_reservation.common.support.scheduler.config;

import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class WaitingQueueSchedulerConfig {

    private final WaitingQueueRepository waitingQueueRepository;

    /**
     * 대기열에서 활성열로 이동
     */
    @Scheduled(fixedRate = 60000)
    public void moveToActiveQueue() {
        try {
            waitingQueueRepository.moveToActiveQueue();
        } catch (Exception e) {
            log.error("[ERROR] WaitingQueue moveToActiveQueue failed={}", e.getMessage(), e);
        }
    }
}
