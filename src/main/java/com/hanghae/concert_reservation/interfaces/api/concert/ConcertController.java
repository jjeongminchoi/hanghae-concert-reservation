package com.hanghae.concert_reservation.interfaces.api.concert;

import com.hanghae.concert_reservation.domain.concert.constant.ConcertDateStatus;
import com.hanghae.concert_reservation.domain.concert.constant.SeatStatus;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ConcertDateResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.response.ConcertDatesResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.response.SeatResponse;
import com.hanghae.concert_reservation.interfaces.api.concert.response.SeatsResponse;
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
    @GetMapping("/api/v1/concerts/{concertId}/concertDates")
    public ResponseEntity<ConcertDatesResponse> getAvailableConcertDates(
            @RequestHeader("token") String token,
            @PathVariable Long concertId,
            @RequestParam ConcertDateStatus status
    ) {
        return ResponseEntity.ok(
                new ConcertDatesResponse(List.of(
                        new ConcertDateResponse(1L, 1L, LocalDateTime.of(2024, 10, 10, 9, 0, 0), "장소A"),
                        new ConcertDateResponse(2L, 1L, LocalDateTime.of(2024, 10, 15, 9, 0, 0), "장소B")
                ))
        );
    }

    /**
     * 콘서트 좌석 조회
     */
    @GetMapping("/api/v1/concerts/{concertId}/concertDates/{concertDateId}/seats")
    public ResponseEntity<SeatsResponse> getSeats(
            @RequestHeader("token") String token,
            @PathVariable Long concertId,
            @PathVariable Long concertDateId
    ) {
        return ResponseEntity.ok(
                new SeatsResponse(
                        List.of(
                                new SeatResponse(10L, 1, 10000, SeatStatus.AVAILABLE),
                                new SeatResponse(11L, 2, 10000, SeatStatus.AVAILABLE)
                        ),
                        List.of(
                                new SeatResponse(12L, 3, 10000, SeatStatus.RESERVED),
                                new SeatResponse(13L, 4, 10000, SeatStatus.TEMPORARILY_RESERVED)
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
