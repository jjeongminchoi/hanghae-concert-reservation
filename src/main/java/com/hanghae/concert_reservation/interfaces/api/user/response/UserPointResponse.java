package com.hanghae.concert_reservation.interfaces.api.user.response;

import lombok.Getter;

@Getter
public class UserPointResponse {

    private Long userId;

    private int amount;

    public UserPointResponse(Long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
