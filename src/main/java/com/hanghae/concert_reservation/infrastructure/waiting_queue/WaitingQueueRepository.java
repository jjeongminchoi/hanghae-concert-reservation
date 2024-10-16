package com.hanghae.concert_reservation.infrastructure.waiting_queue;

import com.hanghae.concert_reservation.domain.waiting_queue.WaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaitingQueueRepository extends JpaRepository<WaitingQueue, Long> {

    @Query("SELECT wq FROM WaitingQueue wq WHERE wq.sessionId = :sessionId")
    WaitingQueue getWaitingQueue(@Param("sessionId") String sessionId);

    Optional<WaitingQueue> findWaitingQueueBySessionId(String sessionId);

    @Query("SELECT wq FROM WaitingQueue wq WHERE wq.waitingQueueStatus = 'WAIT' ORDER BY wq.id ASC LIMIT 100")
    List<WaitingQueue> activeWaitingQueue();

    @Query("SELECT wq FROM WaitingQueue wq WHERE wq.waitingQueueStatus = 'ACTIVE' AND wq.expiredAt < :currentTime")
    List<WaitingQueue> expireWaitingQueue(@Param("currentTime") LocalDateTime currentTime);
}
