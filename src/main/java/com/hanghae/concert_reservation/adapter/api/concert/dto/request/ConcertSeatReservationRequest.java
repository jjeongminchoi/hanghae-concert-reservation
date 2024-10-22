package com.hanghae.concert_reservation.adapter.api.concert.dto.request;

import com.hanghae.concert_reservation.application.concert.dto.command.ConcertSeatReservationCommand;

import java.util.List;

public record ConcertSeatReservationRequest (
        Long userId,
        List<Long> seatIds
) {
    public ConcertSeatReservationCommand toCommand(Long concertId, Long scheduleId) {
        return new ConcertSeatReservationCommand(userId, concertId, scheduleId, seatIds);
    }
}
