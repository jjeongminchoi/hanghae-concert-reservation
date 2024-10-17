package com.hanghae.concert_reservation.domain.concert;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ConcertSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_seat_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long concertScheduleId;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private ConcertSeatStatus concertSeatStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private ConcertSeat(Long concertScheduleId, int seatNumber, BigDecimal price) {
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.concertSeatStatus = ConcertSeatStatus.AVAILABLE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static ConcertSeat of(Long concertScheduleId, int seatNumber, BigDecimal price) {
        return new ConcertSeat(concertScheduleId, seatNumber, price);
    }

    public void changeConcertSeatStatus(ConcertSeatStatus concertSeatStatus) {
        this.concertSeatStatus = concertSeatStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
