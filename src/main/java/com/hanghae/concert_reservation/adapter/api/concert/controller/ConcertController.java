package com.hanghae.concert_reservation.adapter.api.concert.controller;

import com.hanghae.concert_reservation.adapter.api.concert.dto.request.ConcertSeatReservationRequest;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ReservationResponse;
import com.hanghae.concert_reservation.application.concert.usecase.ConcertSeatReservationUseCase;
import com.hanghae.concert_reservation.application.concert.usecase.GetConcertSchedulesUseCase;
import com.hanghae.concert_reservation.application.concert.usecase.GetConcertSeatsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Concert", description = "Concert API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts/{concertId}/schedules/{scheduleId}")
@RestController
public class ConcertController {

    private final GetConcertSchedulesUseCase getConcertSchedulesUseCase;
    private final GetConcertSeatsUseCase getConcertSeatsUseCase;
    private final ConcertSeatReservationUseCase concertSeatReservationUseCase;

    @Operation(summary = "콘서트 일정 조회", description = "예약 가능한 콘서트 일정을 조회합니다.")
    @GetMapping
    public ResponseEntity<ConcertSchedulesResponse> getConcertSchedules(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @RequestParam ConcertScheduleStatus concertScheduleStatus
    ) {
        return ResponseEntity.ok(getConcertSchedulesUseCase.getConcertSchedules(waitingQueueUuid, concertId, concertScheduleStatus));
    }

    @Operation(summary = "콘서트 좌석 조회", description = "콘서트 좌석을 조회합니다.")
    @GetMapping("/seats")
    public ResponseEntity<ConcertSeatsResponse> getSeats(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(getConcertSeatsUseCase.getConcertSeats(waitingQueueUuid, concertId, scheduleId));
    }

    @Operation(summary = "좌석 임시 예약", description = "5분간 점유할 수 있도록 좌석을 임시 예약합니다.")
    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reserve(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @PathVariable Long scheduleId,
            @RequestBody ConcertSeatReservationRequest request
    ) {
        return ResponseEntity.ok(concertSeatReservationUseCase.reservation(waitingQueueUuid, request.toCommand(concertId, scheduleId)));
    }
}
