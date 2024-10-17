package com.hanghae.concert_reservation.adapter.api.user.response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserPointResponse {

    private Long userId;

    private BigDecimal balance;

    public UserPointResponse(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
