package com.hanghae.concert_reservation.application.concert.usecase;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ReservationResponse;
import com.hanghae.concert_reservation.application.concert.dto.command.ConcertSeatReservationCommand;

public interface ConcertSeatReservationUseCase {
    ReservationResponse reservation(String waitingQueueUuid, ConcertSeatReservationCommand command);
}
