package com.hanghae.concert_reservation.adapter.api.concert;

import com.hanghae.concert_reservation.adapter.api.concert.request.ReservationCommand;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.adapter.api.concert.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.adapter.api.concert.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.adapter.api.concert.response.ReservationResponse;
import com.hanghae.concert_reservation.usecase.concert.ConcertUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ConcertController {

    private final ConcertUseCase concertUseCase;

    /**
     * 예약 가능한 콘서트 날짜 조회
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules")
    public ResponseEntity<ConcertSchedulesResponse> getConcertSchedules(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @RequestParam ConcertScheduleStatus concertScheduleStatus
    ) {
        return ResponseEntity.ok(concertUseCase.getConcertSchedules(waitingQueueUuid, concertId, concertScheduleStatus));
    }

    /**
     * 콘서트 좌석 조회
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules/{concertScheduleId}/seats")
    public ResponseEntity<ConcertSeatsResponse> getSeats(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @PathVariable Long concertId,
            @PathVariable Long concertScheduleId
    ) {
        return ResponseEntity.ok(concertUseCase.getConcertSeats(waitingQueueUuid, concertId, concertScheduleId));
    }

    /**
     * 좌석 임시 예약 요청
     */
    @PostMapping("/api/v1/reservation")
    public ResponseEntity<ReservationResponse> reserve(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @RequestBody ReservationCommand command
    ) {
        return ResponseEntity.ok(concertUseCase.reservation(waitingQueueUuid, command));
    }
}
