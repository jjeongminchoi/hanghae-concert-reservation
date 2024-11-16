package com.hanghae.concert_reservation.domain.payment.event;

public interface PaymentEventPublisher {

    void publish(PaymentInfoEvent eventInfo);
}
