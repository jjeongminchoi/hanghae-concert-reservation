package com.hanghae.concert_reservation.adapter.api.waiting_queue;

import com.hanghae.concert_reservation.usecase.waiting_queue.WaitingQueueUseCase;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class WaitingQueueController {

    private final WaitingQueueUseCase waitingQueueUseCase;

    /**
     * 대기열 토큰 발급
     */
    @PostMapping("/api/v1/waiting-queue")
    public ResponseEntity<WaitingQueueCreateResponse> createWaitingQueue(@RequestHeader("SESSION_ID") String sessionId) {
        return ResponseEntity.ok(waitingQueueUseCase.createWaitingQueue(sessionId));
    }

    /**
     * 대기열 토큰 조회
     */
    @GetMapping("/api/v1/waiting-queue")
    public ResponseEntity<WaitingQueueResponse> getWaitingQueue(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid
    ) {
        return ResponseEntity.ok(waitingQueueUseCase.getWaitingQueue(waitingQueueUuid));
    }
}
