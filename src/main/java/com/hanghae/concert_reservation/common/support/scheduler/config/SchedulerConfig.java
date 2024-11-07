package com.hanghae.concert_reservation.common.support.scheduler.config;

import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class SchedulerConfig {

    private final WaitingQueueRepository waitingQueueRepository;

    /**
     * 대기열에서 활성열로 이동
     */
    @Scheduled(fixedRate = 60000)
    public void moveToActiveQueue() {
        waitingQueueRepository.moveToActiveQueue();
    }
}
