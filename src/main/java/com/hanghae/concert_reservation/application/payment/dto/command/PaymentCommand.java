package com.hanghae.concert_reservation.application.payment.dto.command;

public record PaymentCommand(
        Long userId,
        Long reservationId
) {
}
