package com.hanghae.concert_reservation.domain.payment.event.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentInfoPayload {

    private Long concertSeatId;

    private String concertName;

    private BigDecimal amount;

    public PaymentInfoPayload(Long concertSeatId, String concertName, BigDecimal amount) {
        this.concertSeatId = concertSeatId;
        this.concertName = concertName;
        this.amount = amount;
    }
}
