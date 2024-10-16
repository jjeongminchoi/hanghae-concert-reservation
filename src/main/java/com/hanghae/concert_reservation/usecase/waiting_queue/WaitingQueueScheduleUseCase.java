package com.hanghae.concert_reservation.usecase.waiting_queue;

public interface WaitingQueueScheduleUseCase {

    void activeWaitingQueue();

    void expireWaitingQueue();
}
