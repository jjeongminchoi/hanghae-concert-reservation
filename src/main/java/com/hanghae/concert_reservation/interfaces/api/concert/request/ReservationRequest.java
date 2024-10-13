package com.hanghae.concert_reservation.interfaces.api.concert.request;

import lombok.Getter;

@Getter
public class ReservationRequest {

    private Long userId;
    private Long concertSeatId;

    public ReservationRequest(Long userId, Long concertSeatId) {
        this.userId = userId;
        this.concertSeatId = concertSeatId;
    }
}
