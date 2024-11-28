package com.hanghae.concert_reservation.domain.outbox.constant;

import lombok.Getter;

@Getter
public enum OutboxStatus {

    INIT("메세지 전송 준비"),
    SUCCESS("메세지 전송 완료");

    private final String desc;

    OutboxStatus(String desc) {
        this.desc = desc;
    }
}
