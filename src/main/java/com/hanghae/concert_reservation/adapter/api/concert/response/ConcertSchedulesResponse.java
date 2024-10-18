package com.hanghae.concert_reservation.adapter.api.concert.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ConcertSchedulesResponse {

    private List<ConcertScheduleResponse> concerts;

    public ConcertSchedulesResponse(List<ConcertScheduleResponse> concerts) {
        this.concerts = concerts;
    }
}
