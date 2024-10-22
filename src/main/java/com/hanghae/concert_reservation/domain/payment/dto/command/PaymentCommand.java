package com.hanghae.concert_reservation.domain.payment.dto.command;

public record PaymentCommand(
        Long userId,
        Long reservationId
) {
}
