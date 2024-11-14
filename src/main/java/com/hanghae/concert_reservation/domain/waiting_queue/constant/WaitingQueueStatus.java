package com.hanghae.concert_reservation.domain.waiting_queue.constant;

import lombok.Getter;

@Getter
public enum WaitingQueueStatus {

    WAIT("대기열 대기 상태"),
    ACTIVE("대기열 활성 상태");

    private final String desc;

    WaitingQueueStatus(String desc) {
        this.desc = desc;
    }
}
