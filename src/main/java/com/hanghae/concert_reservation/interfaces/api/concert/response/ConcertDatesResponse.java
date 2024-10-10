package com.hanghae.concert_reservation.interfaces.api.concert.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ConcertDatesResponse {

    private List<ConcertDateResponse> concerts;

    public ConcertDatesResponse(List<ConcertDateResponse> concerts) {
        this.concerts = concerts;
    }
}
