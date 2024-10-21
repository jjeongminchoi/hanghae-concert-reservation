package com.hanghae.concert_reservation.domain.concert.constant;

import lombok.Getter;

@Getter
public enum ConcertSeatStatus {

    AVAILABLE("좌석 예약 가능한 상태"),
    TEMPORARILY_RESERVED("좌석 임시 예약된 상태"),
    RESERVED("좌석 임시 예약이 결제된 상태");

    private final String desc;

    ConcertSeatStatus(String desc) {
        this.desc = desc;
    }
}
