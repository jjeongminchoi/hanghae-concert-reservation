package com.hanghae.concert_reservation.interfaces.api.queue;

import com.hanghae.concert_reservation.domain.queue.constant.QueueStatus;
import com.hanghae.concert_reservation.interfaces.api.queue.response.CreateQueueResponse;
import com.hanghae.concert_reservation.interfaces.api.queue.response.GetQueueResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class QueueController {

    /**
     * 대기열 토큰 발급
     */
    @PostMapping("/api/v1/queue/{userId}")
    public ResponseEntity<CreateQueueResponse> createToken(@PathVariable Long userId) {
        return ResponseEntity.ok(new CreateQueueResponse(1L, "uuuuuuuuiiiidddd", LocalDateTime.of(2024,10,10,9,0,0)));
    }

    /**
     * 대기열 토큰 조회
     */
    @GetMapping("/api/v1/queue/token/{userId}")
    public ResponseEntity<GetQueueResponse> getToken(@PathVariable Long userId) {
        return ResponseEntity.ok(new GetQueueResponse(
                1L,
                1L,
                QueueStatus.ACTIVE,
                LocalDateTime.of(2024, 10, 10, 9, 0, 0),
                LocalDateTime.of(2024, 10, 10, 9, 10, 0),
                LocalDateTime.of(2024, 10, 10, 9, 20, 0)));
    }
}
