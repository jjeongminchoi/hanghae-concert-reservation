package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ReservationResponse;
import com.hanghae.concert_reservation.application.concert.dto.command.ConcertSeatReservationCommand;
import com.hanghae.concert_reservation.application.concert.usecase.ConcertSeatReservationUseCase;
import com.hanghae.concert_reservation.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConcertSeatReservationInteractor implements ConcertSeatReservationUseCase {

    private final ConcertService concertService;

    @Override
    public ReservationResponse reservation(String waitingQueueUuid, ConcertSeatReservationCommand command) {
        return concertService.reservation(waitingQueueUuid, command);
    }
}
