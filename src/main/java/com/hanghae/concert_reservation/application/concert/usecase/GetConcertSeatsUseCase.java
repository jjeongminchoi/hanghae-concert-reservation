package com.hanghae.concert_reservation.application.concert.usecase;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSeatsResponse;

public interface GetConcertSeatsUseCase {
    ConcertSeatsResponse getConcertSeats(Long concertId, Long concertScheduleId);
}
