package com.hanghae.concert_reservation.domain.payment.event;

import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SimplePaymentEventPublisher implements PaymentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(PaymentInfoEvent infoEvent) {
        applicationEventPublisher.publishEvent(infoEvent);
    }
}
