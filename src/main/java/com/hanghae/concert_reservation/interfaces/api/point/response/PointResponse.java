package com.hanghae.concert_reservation.interfaces.api.point.response;

import lombok.Getter;

@Getter
public class PointResponse {

    private Long userId;

    private int amount;

    public PointResponse(Long userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }
}
