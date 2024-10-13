package com.hanghae.concert_reservation.interfaces.api.point;

import com.hanghae.concert_reservation.interfaces.api.point.request.PointCharge;
import com.hanghae.concert_reservation.interfaces.api.point.response.PointResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PointController {

    /**
     * 포인트 충전
     */
    @PostMapping("/api/v1/points")
    public ResponseEntity<Integer> charge(@RequestBody PointCharge request) {
        return ResponseEntity.ok().build();
    }


    /**
     * 포인트 조회
     */
    @GetMapping("/api/v1/points/{userId}")
    public ResponseEntity<PointResponse> getPoint(@PathVariable Long userId) {
        return ResponseEntity.ok(new PointResponse(1L, 10000));
    }
}
