package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.application.concert.usecase.GetConcertSchedulesUseCase;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetConcertSchedulesInteractor implements GetConcertSchedulesUseCase {

    private final ConcertService concertService;

    @Override
    public ConcertSchedulesResponse getConcertSchedules(String waitingQueueUuid, Long concertId, ConcertScheduleStatus concertScheduleStatus) {
        return concertService.getConcertSchedules(waitingQueueUuid, concertId, concertScheduleStatus);
    }
}
