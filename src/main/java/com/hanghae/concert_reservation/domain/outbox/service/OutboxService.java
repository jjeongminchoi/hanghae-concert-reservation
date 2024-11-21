package com.hanghae.concert_reservation.domain.outbox.service;

import com.hanghae.concert_reservation.domain.outbox.constant.OutboxStatus;
import com.hanghae.concert_reservation.domain.outbox.entity.Outbox;
import com.hanghae.concert_reservation.domain.outbox.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Transactional
    public void publishScheduleEvents() {
        List<Outbox> outboxes = outboxRepository.findByStatus(OutboxStatus.INIT);
        for (Outbox outbox : outboxes) {
            try {
                kafkaTemplate.send(outbox.getEventType(), outbox.getPayload());
                outbox.updateStatus(OutboxStatus.SUCCESS);
            } catch (Exception e) {
                log.error("[ERROR] Outbox publish failed={}", e.getMessage(), e);
            }
        }
    }
}
