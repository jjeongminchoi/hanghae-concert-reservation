package com.hanghae.concert_reservation.domain.concert;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
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
    void changeReservationStatus() {
        // given
        Reservation reservation = Reservation.of(1L, 1L, "아이유콘서트", LocalDateTime.now(), BigDecimal.valueOf(100000));

        // when
        reservation.changeReservationStatus(ReservationStatus.EXPIRED);

        // then
        assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.EXPIRED);
    }
}