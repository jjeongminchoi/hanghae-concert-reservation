package com.hanghae.concert_reservation.domain.payment.event;

import java.math.BigDecimal;

public record PaymentInfoEvent(
    String concertName,
    Long concertSeat,
    BigDecimal amount
) {
}
