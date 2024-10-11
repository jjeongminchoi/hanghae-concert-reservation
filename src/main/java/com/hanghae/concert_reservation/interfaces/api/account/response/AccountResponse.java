package com.hanghae.concert_reservation.interfaces.api.account.response;

import lombok.Getter;

@Getter
public class AccountResponse {

    private int amount;

    public AccountResponse(int amount) {
        this.amount = amount;
    }
}
