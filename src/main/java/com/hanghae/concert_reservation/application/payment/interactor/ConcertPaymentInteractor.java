package com.hanghae.concert_reservation.application.payment.interactor;

import com.hanghae.concert_reservation.adapter.api.payment.dto.response.PaymentResponse;
import com.hanghae.concert_reservation.domain.payment.dto.command.PaymentCommand;
import com.hanghae.concert_reservation.application.payment.usecase.ConcertPaymentUseCase;
import com.hanghae.concert_reservation.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConcertPaymentInteractor implements ConcertPaymentUseCase {

    private final PaymentService paymentService;
    @Override
    public PaymentResponse payment(PaymentCommand command) {
        return paymentService.payment(command);
    }
}
