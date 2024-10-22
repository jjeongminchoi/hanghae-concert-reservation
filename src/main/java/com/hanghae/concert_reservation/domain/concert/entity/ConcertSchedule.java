package com.hanghae.concert_reservation.domain.concert.entity;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_schedule_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long concertId;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConcertScheduleStatus concertScheduleStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private ConcertSchedule(Long concertId, LocalDateTime date, String venue) {
        this.concertId = concertId;
        this.date = date;
        this.venue = venue;
        this.concertScheduleStatus = ConcertScheduleStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static ConcertSchedule of(Long concertId, LocalDateTime date, String venue) {
        return new ConcertSchedule(concertId, date, venue);
    }
}
