package com.hanghae.concert_reservation.domain.concert.entity;

import com.hanghae.concert_reservation.common.exception.BizInvalidException;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "reservation")
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
        this.tempReservedAt = LocalDateTime.now().plusMinutes(5); // 5분간 임시 예약
    }

    public static Reservation of(Long userId, Long concertSeatId, String concertName, LocalDateTime concertDate, BigDecimal price) {
        return new Reservation(userId, concertSeatId, concertName, concertDate, price);
    }

    public void changeReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void validatePayable() {
        if (this.reservationStatus != ReservationStatus.RESERVED) {
            throw new BizInvalidException("예약이 결제 가능한 상태가 아닙니다.");
        }

        if (this.tempReservedAt == null || this.tempReservedAt.isBefore(LocalDateTime.now())) {
            throw new BizInvalidException("임시 예약 시간이 만료되었습니다.");
        }
    }
}
