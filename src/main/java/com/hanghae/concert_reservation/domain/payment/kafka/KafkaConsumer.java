package com.hanghae.concert_reservation.domain.payment.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(byte[] message, Acknowledgment acknowledgment) {
        try {
            String receivedMessage = objectMapper.readValue(message, String.class);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[ERROR] Processing failed={}", e.getMessage(), e);
        }
    }
}
