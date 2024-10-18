package com.hanghae.concert_reservation.domain.concert;

import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false, updatable = false)
    private Long concertSeatId;

    @Column(nullable = false)
    private String concertName;

    @Column(nullable = false)
    private LocalDateTime concertDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime tempReservedAt;

    private Reservation(Long userId, Long concertSeatId, String concertName, LocalDateTime concertDate, BigDecimal price) {
        this.userId = userId;
        this.concertSeatId = concertSeatId;
        this.concertName = concertName;
        this.concertDate = concertDate;
        this.price = price;
        this.reservationStatus = ReservationStatus.RESERVED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Reservation of(Long userId, Long concertSeatId, String concertName, LocalDateTime concertDate, BigDecimal price) {
        return new Reservation(userId, concertSeatId, concertName, concertDate, price);
    }

    public void setToTemporaryReservationTime() {
        this.tempReservedAt = LocalDateTime.now().plusMinutes(5); // 5분간 임시 예약
        this.updatedAt = LocalDateTime.now();
    }

    public void changeReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
