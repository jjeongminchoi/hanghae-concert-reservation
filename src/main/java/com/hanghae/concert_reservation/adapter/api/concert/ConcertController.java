package com.hanghae.concert_reservation.adapter.api.concert;

import com.hanghae.concert_reservation.adapter.api.concert.request.ReservationCommand;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.adapter.api.concert.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.adapter.api.concert.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.adapter.api.concert.response.ReservationResponse;
import com.hanghae.concert_reservation.usecase.concert.ConcertUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Concert", description = "Concert API")
@RequiredArgsConstructor
@RestController
public class ConcertController {

    private final ConcertUseCase concertUseCase;

    @Operation(summary = "콘서트 일정 조회", description = "예약 가능한 콘서트 일정을 조회합니다.")
    @GetMapping("/api/v1/concerts/{concertId}/schedules")
    public ResponseEntity<ConcertSchedulesResponse> getConcertSchedules(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @RequestParam ConcertScheduleStatus concertScheduleStatus
    ) {
        return ResponseEntity.ok(concertUseCase.getConcertSchedules(waitingQueueUuid, concertId, concertScheduleStatus));
    }

    @Operation(summary = "콘서트 좌석 조회", description = "콘서트 좌석을 조회합니다.")
    @GetMapping("/api/v1/concerts/{concertId}/schedules/{concertScheduleId}/seats")
    public ResponseEntity<ConcertSeatsResponse> getSeats(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @PathVariable Long concertScheduleId
    ) {
        return ResponseEntity.ok(concertUseCase.getConcertSeats(waitingQueueUuid, concertId, concertScheduleId));
    }

    @Operation(summary = "좌석 임시 예약", description = "5분간 점유할 수 있도록 좌석을 임시 예약합니다.")
    @PostMapping("/api/v1/reservation")
    public ResponseEntity<ReservationResponse> reserve(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @RequestBody ReservationCommand command
    ) {
        return ResponseEntity.ok(concertUseCase.reservation(waitingQueueUuid, command));
    }
}
