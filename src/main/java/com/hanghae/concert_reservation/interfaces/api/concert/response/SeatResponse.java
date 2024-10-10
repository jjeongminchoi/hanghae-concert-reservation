package com.hanghae.concert_reservation.interfaces.api.concert.response;

import com.hanghae.concert_reservation.domain.concert.constant.SeatStatus;

public record SeatResponse(
        Long concertSeatId,
        int seatNumber,
        int price,
        SeatStatus status
) {
}
