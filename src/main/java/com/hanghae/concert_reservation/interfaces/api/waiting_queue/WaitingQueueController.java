package com.hanghae.concert_reservation.interfaces.api.waiting_queue;

import com.hanghae.concert_reservation.domain.waiting_queue.constant.WaitingQueueStatus;
import com.hanghae.concert_reservation.interfaces.api.waiting_queue.request.CreateWaitingQueue;
import com.hanghae.concert_reservation.interfaces.api.waiting_queue.response.CreateWaitingQueueResponse;
import com.hanghae.concert_reservation.interfaces.api.waiting_queue.response.GetWaitingQueueResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class WaitingQueueController {

    /**
     * 대기열 토큰 발급
     */
    @PostMapping("/api/v1/waiting-queue")
    public ResponseEntity<CreateWaitingQueueResponse> createToken(@RequestBody CreateWaitingQueue request) {
        return ResponseEntity.ok(new CreateWaitingQueueResponse("uuuuuuuuiiiidddd"));
    }

    /**
     * 대기열 토큰 조회
     */
    @GetMapping("/api/v1/waiting-queue/{userId}")
    public ResponseEntity<GetWaitingQueueResponse> getToken(
            @RequestHeader("token") String token,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(new GetWaitingQueueResponse(
                1L,
                1L,
                100L,
                WaitingQueueStatus.ACTIVE,
                LocalDateTime.of(2024, 10, 10, 9, 0, 0),
                LocalDateTime.of(2024, 10, 10, 9, 20, 0)));
    }
}
