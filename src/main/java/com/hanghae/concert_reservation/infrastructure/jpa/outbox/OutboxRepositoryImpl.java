package com.hanghae.concert_reservation.infrastructure.jpa.outbox;

import com.hanghae.concert_reservation.domain.outbox.constant.OutboxStatus;
import com.hanghae.concert_reservation.domain.outbox.entity.Outbox;
import com.hanghae.concert_reservation.domain.outbox.repository.OutboxRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public List<Outbox> findByStatus(OutboxStatus status) {
        return outboxJpaRepository.findByStatus(status);
    }

    @Override
    public void save(Outbox outbox) {
        outboxJpaRepository.save(outbox);
    }
}
