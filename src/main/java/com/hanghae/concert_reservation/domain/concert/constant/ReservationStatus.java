package com.hanghae.concert_reservation.domain.concert.constant;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    RESERVED("예약 완료"),
    CANCELLED("예약 취소"),
    EXPIRED("예약 만료"),
    PAYMENT("예약 결제");

    private final String desc;

    ReservationStatus(String desc) {
        this.desc = desc;
    }
}
