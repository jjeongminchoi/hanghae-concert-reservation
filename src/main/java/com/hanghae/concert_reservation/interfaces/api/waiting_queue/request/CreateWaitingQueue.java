package com.hanghae.concert_reservation.interfaces.api.waiting_queue.request;

import lombok.Getter;

@Getter
public class CreateWaitingQueue {

    private Long userId;

    private Long concertId;

    public CreateWaitingQueue(Long userId, Long concertId) {
        this.userId = userId;
        this.concertId = concertId;
    }
}
