package com.hanghae.concert_reservation.adapter.api.concert.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ConcertSeatsResponse {

    private List<ConcertSeatResponse> availableSeats;
    private List<ConcertSeatResponse> unAvailableSeats;

    public ConcertSeatsResponse(List<ConcertSeatResponse> availableSeats, List<ConcertSeatResponse> unAvailableSeats) {
        this.availableSeats = availableSeats;
        this.unAvailableSeats = unAvailableSeats;
    }
}
