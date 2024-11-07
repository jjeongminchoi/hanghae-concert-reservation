package com.hanghae.concert_reservation.domain.waiting_queue.repository;

public interface WaitingQueueRepository {
    String addToWaitingQueue(String sessionId);

    void moveToActiveQueue();

    void existsActiveWaitingQueue(String waitingQueueUuid);
}
