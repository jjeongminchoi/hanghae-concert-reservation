package com.hanghae.concert_reservation.adapter.api.waiting_queue;

import com.hanghae.concert_reservation.usecase.waiting_queue.WaitingQueueUseCase;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WaitingQueue", description = "WaitingQueue API")
@RequiredArgsConstructor
@RestController
public class WaitingQueueController {

    private final WaitingQueueUseCase waitingQueueUseCase;

    @Operation(summary = "대기열 토큰 발급", description = "10분간 유요한 대기열 토큰을 발급합니다.")
    @PostMapping("/api/v1/waiting-queue")
    public ResponseEntity<WaitingQueueCreateResponse> createWaitingQueue(@RequestHeader("SESSION_ID") String sessionId) {
        return ResponseEntity.ok(waitingQueueUseCase.createWaitingQueue(sessionId));
    }

    /**
     * 대기열 토큰 조회
     */
    @Operation(summary = "대기열 토큰 조회", description = "대기열 토큰을 조회합니다.")
    @GetMapping("/api/v1/waiting-queue")
    public ResponseEntity<WaitingQueueResponse> getWaitingQueue(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid
    ) {
        return ResponseEntity.ok(waitingQueueUseCase.getWaitingQueue(waitingQueueUuid));
    }
}
