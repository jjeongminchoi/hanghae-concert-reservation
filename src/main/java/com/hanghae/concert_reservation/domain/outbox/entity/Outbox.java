package com.hanghae.concert_reservation.domain.outbox.entity;

import com.hanghae.concert_reservation.domain.outbox.constant.OutboxStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(
        name = "outbox",
        uniqueConstraints = @UniqueConstraint(columnNames = {"eventKey", "eventType"}),
        indexes = @Index(name = "idx_status", columnList = "status")
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbox_id")
    private Long id;

    @Column(nullable = false)
    private String eventKey;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private Outbox(String eventKey, String eventType, String payload) {
        this.eventKey = eventKey;
        this.eventType = eventType;
        this.payload = payload;
        this.status = OutboxStatus.INIT;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Outbox of(String eventKey, String eventType, String payload) {
        return new Outbox(eventKey, eventType, payload);
    }

    public void updateStatus(OutboxStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
