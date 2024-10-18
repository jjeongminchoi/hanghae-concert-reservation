package com.hanghae.concert_reservation.adapter.api.concert.request;

import lombok.Getter;

@Getter
public class ReservationCommand {

    private Long userId;
    private Long concertSeatId;

    public ReservationCommand(Long userId, Long concertSeatId) {
        this.userId = userId;
        this.concertSeatId = concertSeatId;
    }
}
