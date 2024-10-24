package com.hanghae.concert_reservation.application.payment.usecase;

import com.hanghae.concert_reservation.adapter.api.payment.dto.response.PaymentResponse;
import com.hanghae.concert_reservation.domain.payment.dto.command.PaymentCommand;

public interface ConcertPaymentUseCase {

    PaymentResponse payment(PaymentCommand command);
}
