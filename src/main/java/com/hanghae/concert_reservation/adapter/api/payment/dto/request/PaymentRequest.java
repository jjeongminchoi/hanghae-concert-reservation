package com.hanghae.concert_reservation.adapter.api.payment.dto.request;

import com.hanghae.concert_reservation.application.payment.dto.command.PaymentCommand;

public record PaymentRequest(
        Long userId,
        Long reservationId
) {
    public PaymentCommand toCommand() {
        return new PaymentCommand(userId, reservationId);
    }
}
