package com.hanghae.concert_reservation.adapter.api.concert.dto.request;

import com.hanghae.concert_reservation.domain.concert.dto.command.ConcertSeatReservationCommand;

import java.util.List;

public record ConcertSeatReservationRequest (
        Long userId,
        Long seatId
) {
    public ConcertSeatReservationCommand toCommand(Long concertId, Long scheduleId) {
        return new ConcertSeatReservationCommand(userId, concertId, scheduleId, seatId);
    }
}
