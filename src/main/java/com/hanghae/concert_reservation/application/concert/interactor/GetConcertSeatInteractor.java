package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.application.concert.usecase.GetConcertSeatsUseCase;
import com.hanghae.concert_reservation.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetConcertSeatInteractor implements GetConcertSeatsUseCase {

    private final ConcertService concertService;

    @Override
    public ConcertSeatsResponse getConcertSeats(String waitingQueueUuid, Long concertId, Long concertScheduleId) {
        return concertService.getConcertSeats(waitingQueueUuid, concertId, concertScheduleId);
    }
}
