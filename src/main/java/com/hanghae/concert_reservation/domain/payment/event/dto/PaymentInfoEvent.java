package com.hanghae.concert_reservation.domain.payment.event.dto;

import java.math.BigDecimal;

public record PaymentInfoEvent(
        String eventKey,
        String eventType,
        Long concertSeatId,
        String concertName,
        BigDecimal amount
) {
}
