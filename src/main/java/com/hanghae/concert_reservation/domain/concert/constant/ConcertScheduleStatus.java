package com.hanghae.concert_reservation.domain.concert.constant;

import lombok.Getter;

@Getter
public enum ConcertScheduleStatus {

    OPEN("일정 활성 상태"),
    CLOSE("일정 마감 상태");

    private final String desc;

    ConcertScheduleStatus(String desc) {
        this.desc = desc;
    }
}
