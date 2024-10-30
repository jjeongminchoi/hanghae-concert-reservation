package com.hanghae.concert_reservation.domain.waiting_queue.entity;

import com.hanghae.concert_reservation.domain.waiting_queue.constant.WaitingQueueStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "waiting_queue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WaitingQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_queue_id")
    private Long id;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private String waitingQueueUuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WaitingQueueStatus waitingQueueStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private WaitingQueue(String sessionId, String waitingQueueUuid) {
        this.sessionId = sessionId;
        this.waitingQueueUuid = waitingQueueUuid;
        this.waitingQueueStatus = WaitingQueueStatus.WAIT;
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static WaitingQueue from(String sessionId, String waitingQueueUuid) {
        return new WaitingQueue(sessionId, waitingQueueUuid);
    }

    public void activateWaitingQueue() {
        this.waitingQueueStatus = WaitingQueueStatus.ACTIVE;
        this.expiredAt = LocalDateTime.now().plusMinutes(10);
    }

    public void expireWaitingQueue() {
        this.waitingQueueStatus = WaitingQueueStatus.EXPIRE;
        this.updatedAt = LocalDateTime.now();
    }
}
