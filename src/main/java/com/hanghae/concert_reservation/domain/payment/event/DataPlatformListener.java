package com.hanghae.concert_reservation.domain.payment.event;

public interface DataPlatformListener {

    void sendPaymentInfo(PaymentInfoEvent eventInfo);
}
