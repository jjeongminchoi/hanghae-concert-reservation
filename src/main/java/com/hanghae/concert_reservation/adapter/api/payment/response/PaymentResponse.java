package com.hanghae.concert_reservation.adapter.api.payment.response;

import lombok.Getter;

@Getter
public class PaymentResponse {

    private Long paymentId;

    public PaymentResponse(Long paymentId) {
        this.paymentId = paymentId;
    }
}
