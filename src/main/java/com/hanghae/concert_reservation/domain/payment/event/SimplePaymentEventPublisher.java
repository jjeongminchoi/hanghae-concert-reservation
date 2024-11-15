package com.hanghae.concert_reservation.domain.payment.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SimplePaymentEventPublisher implements PaymentEventPublisher {

    private final DataPlatformListener dataPlatformListener;

    @Override
    public void publish(PaymentInfoEvent infoEvent) {
        dataPlatformListener.sendPaymentInfo(infoEvent);
    }
}
