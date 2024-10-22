package com.hanghae.concert_reservation.infrastructure.waiting_queue.repository;

import com.hanghae.concert_reservation.domain.waiting_queue.constant.WaitingQueueStatus;
import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueue, Long> {

    Optional<WaitingQueue> findBySessionId(String sessionId);

    Optional<WaitingQueue> findByWaitingQueueUuid(String waitingQueueUuid);

    @Query("SELECT wq FROM WaitingQueue wq WHERE wq.waitingQueueStatus = 'ACTIVE' AND wq.waitingQueueUuid = :waitingQueueUuid")
    Optional<WaitingQueue> getActiveWaitingQueueByWaitingQueueUuid(String waitingQueueUuid);

    @Query("SELECT wq FROM WaitingQueue wq WHERE wq.waitingQueueStatus = 'WAIT' ORDER BY wq.id ASC LIMIT 100")
    List<WaitingQueue> activeWaitingQueue();

    @Query("SELECT wq FROM WaitingQueue wq WHERE wq.waitingQueueStatus = 'ACTIVE' AND wq.expiredAt < :currentTime")
    List<WaitingQueue> expireWaitingQueue(@Param("currentTime") LocalDateTime currentTime);
}
