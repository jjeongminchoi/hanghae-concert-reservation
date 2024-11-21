package com.hanghae.concert_reservation.domain.outbox.repository;

import com.hanghae.concert_reservation.domain.outbox.constant.OutboxStatus;
import com.hanghae.concert_reservation.domain.outbox.entity.Outbox;

import java.util.List;

public interface OutboxRepository {

    List<Outbox> findByStatus(OutboxStatus status);
    void save(Outbox outbox);
}
