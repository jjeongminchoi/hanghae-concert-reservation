package com.hanghae.concert_reservation.application.concert.dto.command;

public record ConcertSeatReservationCommand(
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId
) {
}
