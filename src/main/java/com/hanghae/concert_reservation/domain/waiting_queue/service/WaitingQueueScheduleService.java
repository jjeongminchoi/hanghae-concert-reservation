package com.hanghae.concert_reservation.domain.waiting_queue.service;

import com.hanghae.concert_reservation.domain.waiting_queue.WaitingQueue;
import com.hanghae.concert_reservation.infrastructure.waiting_queue.WaitingQueueRepository;
import com.hanghae.concert_reservation.usecase.waiting_queue.WaitingQueueScheduleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@EnableScheduling
@Transactional
@RequiredArgsConstructor
@Service
public class WaitingQueueScheduleService implements WaitingQueueScheduleUseCase {

    private final WaitingQueueRepository waitingQueueRepository;

    /**
     * 대기열 활성 스케줄
     * 5분마다 Queue 의 상태가 WAIT 인 Queue 를 앞에서 100명씩 입장시킨다. (WAIT -> ACTIVE / expiredAt: 현재시간 + 10분)
     */
    @Scheduled(cron = "0 */5 * * * *")
    @Override
    public void activeWaitingQueue() {
        waitingQueueRepository.activeWaitingQueue().forEach(WaitingQueue::activateWaitingQueue);
    }

    /**
     * 대기열 만료 스케줄
     * 10분마다 Queue 의 상태가 ACTIVE 이면서 expiredAt이 10분 경과한 Queue 를 EXPIRED 로 만료 시킨다.
     */
    @Scheduled(cron = "0 */10 * * * *")
    @Override
    public void expireWaitingQueue() {
        waitingQueueRepository.expireWaitingQueue(LocalDateTime.now()).forEach(WaitingQueue::expireWaitingQueue);
    }
}
