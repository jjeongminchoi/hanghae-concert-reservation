package com.hanghae.concert_reservation.interfaces.api.reservation;

import com.hanghae.concert_reservation.interfaces.api.reservation.request.ReservationRequest;
import com.hanghae.concert_reservation.interfaces.api.reservation.response.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    /**
     * 좌석 (임시)예약 요청
     */
    @PostMapping("/api/v1/reservation")
    public ResponseEntity<ReservationResponse> reserve(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok().body(new ReservationResponse(1L));
    }
}
