package com.hanghae.concert_reservation.domain.payment.event;

public interface DataPlatformService {

    void service(PaymentInfoEvent eventInfo);
}
