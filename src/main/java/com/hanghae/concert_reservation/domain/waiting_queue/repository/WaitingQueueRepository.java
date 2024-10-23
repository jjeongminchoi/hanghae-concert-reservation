package com.hanghae.concert_reservation.domain.waiting_queue.repository;

import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;

import java.time.LocalDateTime;
import java.util.List;

public interface WaitingQueueRepository {

    WaitingQueue getWaitingQueue(String sessionId);
    void existsActiveWaitingQueue(String waitingQueueUuid);
    void existWaitingQueueBySessionId(String sessionId);
    List<WaitingQueue> activeWaitingQueue();
    List<WaitingQueue> expireWaitingQueue(LocalDateTime currentTime);
    WaitingQueue save(WaitingQueue waitingQueue);
}
