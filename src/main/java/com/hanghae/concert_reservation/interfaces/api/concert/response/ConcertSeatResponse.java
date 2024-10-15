package com.hanghae.concert_reservation.interfaces.api.concert.response;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;

public record ConcertSeatResponse(
        Long concertSeatId,
        int seatNumber,
        int price,
        ConcertSeatStatus status
) {
}