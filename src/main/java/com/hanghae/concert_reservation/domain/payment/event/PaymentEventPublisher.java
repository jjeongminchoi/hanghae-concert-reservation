package com.hanghae.concert_reservation.domain.payment.event;

import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoEvent;

public interface PaymentEventPublisher {

    void publish(PaymentInfoEvent eventInfo);
}
