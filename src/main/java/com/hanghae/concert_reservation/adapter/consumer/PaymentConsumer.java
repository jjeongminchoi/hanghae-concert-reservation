package com.hanghae.concert_reservation.adapter.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.concert_reservation.adapter.external.DataPlatform;
import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentConsumer {

    private final ObjectMapper objectMapper;
    private final DataPlatform dataPlatform;

    @KafkaListener(topics = "payment-external-info", groupId = "my-group")
    public void listen(byte[] message, Acknowledgment acknowledgment) {
        try {
            dataPlatform.service(objectMapper.writeValueAsString(message));
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[ERROR] Processing failed={}", e.getMessage(), e);
        }
    }
}
