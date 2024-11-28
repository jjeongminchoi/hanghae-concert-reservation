package com.hanghae.concert_reservation.adapter.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoEvent;

public interface DataPlatformListener {

    void saveOutbox(PaymentInfoEvent event) throws JsonProcessingException;

    void sendPaymentInfo(PaymentInfoEvent event);
}
