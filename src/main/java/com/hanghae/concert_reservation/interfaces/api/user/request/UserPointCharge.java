package com.hanghae.concert_reservation.interfaces.api.user.request;

import lombok.Getter;

@Getter
public class UserPointCharge {

    private Long userId;

    private int amount;

    public UserPointCharge(Long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
