package com.hanghae.concert_reservation.domain.payment.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.concert_reservation.infrastructure.kafka.payment.PaymentProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaProducerTest {

    @Autowired
    private PaymentProducer paymentProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static String receivedMessage;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(byte[] message) throws IOException {
        receivedMessage = objectMapper.readValue(message, String.class);
        System.out.println("receivedMessage = " + receivedMessage);
    }

    @Test
    void testSendMessage() throws InterruptedException {
        // Given
        String topic = "test-topic";
        Object message = "{message: test topic message}";

        // When
        paymentProducer.sendMessage(topic, message);
        Thread.sleep(2000);

        // Then
        assertThat(receivedMessage).isEqualTo(message);
    }
}
