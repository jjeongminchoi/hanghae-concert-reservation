package com.hanghae.concert_reservation.domain.concert;

import com.hanghae.concert_reservation.common.exception.BizInvalidException;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ConcertTest {

    @Test
    void changeConcertSeatStatus() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1, BigDecimal.valueOf(100000));

        // when
        concertSeat.changeConcertSeatStatus(ConcertSeatStatus.TEMPORARILY_RESERVED);

        // then
        assertThat(concertSeat.getConcertSeatStatus()).isEqualTo(ConcertSeatStatus.TEMPORARILY_RESERVED);
    }

    @Test
    void setToTemporaryReservationTime_Should_SetTempReservedAtAndUpdatedAt() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L, "아이유콘서트", now, BigDecimal.valueOf(100000));

        // when
        reservation.setToTemporaryReservationTime();

        // then
        assertThat(reservation.getTempReservedAt()).isAfter(now.plusMinutes(4));
        assertThat(reservation.getTempReservedAt()).isBefore(now.plusMinutes(6));

        assertThat(reservation.getUpdatedAt()).isAfter(now);
        assertThat(reservation.getUpdatedAt()).isBefore(now.plusMinutes(1));
    }

    @Test
    void changeReservationStatus() {
        // given
        Reservation reservation = Reservation.of(1L, 1L, "아이유콘서트", LocalDateTime.now(), BigDecimal.valueOf(100000));

        // when
        reservation.changeReservationStatus(ReservationStatus.EXPIRED);

        // then
        assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.EXPIRED);
    }

    @Test
    void isAvailableSeat() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1, BigDecimal.valueOf(10000));
        concertSeat.changeConcertSeatStatus(ConcertSeatStatus.TEMPORARILY_RESERVED);

        // exception
        assertThatThrownBy(concertSeat::isAvailable)
                .isInstanceOf(BizInvalidException.class)
                .hasMessageContaining("예약할 수 없는 좌석입니다");
    }
}