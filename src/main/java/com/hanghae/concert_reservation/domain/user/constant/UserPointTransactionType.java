package com.hanghae.concert_reservation.domain.user.constant;

import lombok.Getter;

@Getter
public enum UserPointTransactionType {

    CHARGE("유저 포인트 충전 시"),
    PAYMENT("유저 포인트 결제 시");

    private final String desc;

    UserPointTransactionType(String desc) {
        this.desc = desc;
    }
}
