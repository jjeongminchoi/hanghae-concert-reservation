package com.hanghae.concert_reservation.interfaces.api.reservation.request;

import lombok.Getter;

@Getter
public class ReservationRequest {

    private Long reservationId;
    private Long seatId;

    public ReservationRequest(Long reservationId, Long seatId) {
        this.reservationId = reservationId;
        this.seatId = seatId;
    }
}
