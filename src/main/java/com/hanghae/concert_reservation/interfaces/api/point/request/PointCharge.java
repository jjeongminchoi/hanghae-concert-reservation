package com.hanghae.concert_reservation.interfaces.api.point.request;

import lombok.Getter;

@Getter
public class PointCharge {

    private Long userId;

    private int amount;

    public PointCharge(Long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
