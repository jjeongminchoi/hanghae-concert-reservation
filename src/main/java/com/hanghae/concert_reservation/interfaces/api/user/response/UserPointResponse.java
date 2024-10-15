package com.hanghae.concert_reservation.interfaces.api.user.response;

import lombok.Getter;

@Getter
public class UserPointResponse {

    private Long userId;

    private int balance;

    public UserPointResponse(Long userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
