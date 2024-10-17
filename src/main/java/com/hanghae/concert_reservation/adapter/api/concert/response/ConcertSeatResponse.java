package com.hanghae.concert_reservation.adapter.api.concert.response;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;

import java.math.BigDecimal;

public record ConcertSeatResponse(
        Long concertSeatId,
        int seatNumber,
        BigDecimal price,
        ConcertSeatStatus concertSeatStatus
) {
}
