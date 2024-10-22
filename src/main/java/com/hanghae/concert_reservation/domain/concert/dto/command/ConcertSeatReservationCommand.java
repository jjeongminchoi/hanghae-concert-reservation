package com.hanghae.concert_reservation.domain.concert.dto.command;

public record ConcertSeatReservationCommand(
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId
) {
}
