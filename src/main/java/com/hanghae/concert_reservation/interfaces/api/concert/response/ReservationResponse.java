package com.hanghae.concert_reservation.interfaces.api.concert.response;

import lombok.Getter;

@Getter
public class ReservationResponse {

    private Long reservationId;

    public ReservationResponse(Long reservationId) {
        this.reservationId = reservationId;
    }
}
