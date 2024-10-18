package com.hanghae.concert_reservation.usecase.payment;

import com.hanghae.concert_reservation.adapter.api.payment.request.PaymentCommand;
import com.hanghae.concert_reservation.adapter.api.payment.response.PaymentResponse;

public interface PaymentUseCase {

    PaymentResponse payment(String waitingQueueUuid, PaymentCommand command);
}
