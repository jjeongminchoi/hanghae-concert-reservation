package com.hanghae.concert_reservation.adapter.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.concert_reservation.adapter.external.DataPlatformService;
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
    private final DataPlatformService dataPlatformService;

    @KafkaListener(topics = "payment-external-info", groupId = "my-group")
    public void listen(byte[] message, Acknowledgment acknowledgment) {
        try {
            String info = objectMapper.readValue(message, String.class);
            dataPlatformService.service(info);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[ERROR] Processing failed={}", e.getMessage(), e);
        }
    }
}
