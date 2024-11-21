package com.hanghae.concert_reservation.infrastructure.outbox;

import com.hanghae.concert_reservation.domain.outbox.constant.OutboxStatus;
import com.hanghae.concert_reservation.domain.outbox.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    @Query("SELECT o FROM Outbox o WHERE o.status = :status")
    List<Outbox> findByStatus(@Param("status") OutboxStatus status);
}
