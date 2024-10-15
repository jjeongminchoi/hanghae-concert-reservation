package com.hanghae.concert_reservation.interfaces.api.concert;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ConcertScheduleResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ConcertSeatResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.request.ReservationRequest;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ConcertController {

    /**
     * 예약 가능한 콘서트 날짜 조회
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules")
    public ResponseEntity<ConcertSchedulesResponse> getAvailableConcertSchedules(
            @RequestHeader("token") String token,
            @PathVariable Long concertId,
            @RequestParam ConcertScheduleStatus status
    ) {
        return ResponseEntity.ok(
                new ConcertSchedulesResponse(List.of(
                        new ConcertScheduleResponse(1L, 1L, LocalDateTime.of(2024, 10, 10, 9, 0, 0), "장소A"),
                        new ConcertScheduleResponse(2L, 1L, LocalDateTime.of(2024, 10, 15, 9, 0, 0), "장소B")
                ))
        );
    }

    /**
     * 콘서트 좌석 조회
     */
    @GetMapping("/api/v1/concerts/{concertId}/schedules/{concertScheduleId}/seats")
    public ResponseEntity<ConcertSeatsResponse> getSeats(
            @RequestHeader("token") String token,
            @PathVariable Long concertId,
            @PathVariable Long concertScheduleId
    ) {
        return ResponseEntity.ok(
                new ConcertSeatsResponse(
                        List.of(
                                new ConcertSeatResponse(10L, 1, 10000, ConcertSeatStatus.AVAILABLE),
                                new ConcertSeatResponse(11L, 2, 10000, ConcertSeatStatus.AVAILABLE)
                        ),
                        List.of(
                                new ConcertSeatResponse(12L, 3, 10000, ConcertSeatStatus.RESERVED),
                                new ConcertSeatResponse(13L, 4, 10000, ConcertSeatStatus.TEMPORARILY_RESERVED)
                        )
                )
        );
    }

    /**
     * 좌석 임시 예약 요청
     */
    @PostMapping("/api/v1/reservation")
    public ResponseEntity<ReservationResponse> reserve(
            @RequestHeader("token") String token,
            @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok().body(new ReservationResponse(1L));
    }
}
