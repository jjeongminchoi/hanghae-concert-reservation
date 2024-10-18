package com.hanghae.concert_reservation.usecase.concert;

import com.hanghae.concert_reservation.adapter.api.concert.request.ReservationCommand;
import com.hanghae.concert_reservation.adapter.api.concert.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.adapter.api.concert.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.adapter.api.concert.response.ReservationResponse;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;

public interface ConcertUseCase {

    ConcertSchedulesResponse getConcertSchedules(String waitingQueueUuid, Long concertId, ConcertScheduleStatus concertScheduleStatus);

    ConcertSeatsResponse getConcertSeats(String waitingQueueUuid, Long concertId, Long concertScheduleId);

    ReservationResponse reservation(String waitingQueueUuid, ReservationCommand command);
}
