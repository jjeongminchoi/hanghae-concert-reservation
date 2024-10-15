package com.hanghae.concert_reservation.interfaces.api.waiting_queue.request;

import lombok.Getter;

@Getter
public class CreateWaitingQueue {

    private Long sessionId;

    public CreateWaitingQueue(Long sessionId) {
        this.sessionId = sessionId;
    }
}
