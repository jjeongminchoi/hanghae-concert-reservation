package com.hanghae.concert_reservation.adapter.api.payment.request;

import lombok.Getter;

@Getter
public class PaymentRequest {

    private Long userId;
    private Long reservationId;

    public PaymentRequest(Long userId, Long reservationId) {
        this.userId = userId;
        this.reservationId = reservationId;
    }
}
