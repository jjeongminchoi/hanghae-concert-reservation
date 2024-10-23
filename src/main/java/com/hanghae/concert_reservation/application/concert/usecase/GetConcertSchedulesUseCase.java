package com.hanghae.concert_reservation.application.concert.usecase;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;

public interface GetConcertSchedulesUseCase {

    ConcertSchedulesResponse getConcertSchedules(Long concertId, ConcertScheduleStatus concertScheduleStatus);
}
