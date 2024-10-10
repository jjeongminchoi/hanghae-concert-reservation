package com.hanghae.concert_reservation.interfaces.api.concert.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SeatsResponse {

    private List<SeatResponse> availableSeats;
    private List<SeatResponse> unAvailableSeats;

    public SeatsResponse(List<SeatResponse> availableSeats, List<SeatResponse> unAvailableSeats) {
        this.availableSeats = availableSeats;
        this.unAvailableSeats = unAvailableSeats;
    }
}
