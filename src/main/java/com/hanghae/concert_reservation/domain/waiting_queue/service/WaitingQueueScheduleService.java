package com.hanghae.concert_reservation.domain.waiting_queue.service;

import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Service
public class WaitingQueueScheduleService {

    private final WaitingQueueRepository waitingQueueRepository;

    public void activeWaitingQueue() {
        waitingQueueRepository.activeWaitingQueue().forEach(WaitingQueue::activateWaitingQueue);
    }

    public void expireWaitingQueue() {
        waitingQueueRepository.expireWaitingQueue(LocalDateTime.now()).forEach(WaitingQueue::expireWaitingQueue);
    }
}
