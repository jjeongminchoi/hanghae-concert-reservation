package com.hanghae.concert_reservation.adapter.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.concert_reservation.domain.outbox.service.OutboxService;
import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoEvent;
import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoPayload;
import com.hanghae.concert_reservation.infrastructure.kafka.payment.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleDataPlatformListener implements DataPlatformListener {

    private final OutboxService outboxService;
    private final PaymentProducer paymentProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Override
    public void saveOutbox(PaymentInfoEvent event) throws JsonProcessingException {
        Object payload = null;
        if ("PaymentExternalEvent".equals(event.eventType())) {
            payload = new PaymentInfoPayload(event.concertSeatId(), event.concertName(), event.amount());
        }
        outboxService.createOutbox(event.eventKey(), event.eventType(), payload);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void sendPaymentInfo(PaymentInfoEvent event) {
        paymentProducer.sendMessage("payment-external-info", event);
    }
}
