package com.hanghae.concert_reservation.adapter.api.payment.request;

import lombok.Getter;

@Getter
public class PaymentCommand {

    private Long userId;
    private Long reservationId;

    public PaymentCommand(Long userId, Long reservationId) {
        this.userId = userId;
        this.reservationId = reservationId;
    }
}
