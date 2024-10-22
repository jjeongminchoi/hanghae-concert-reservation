package com.hanghae.concert_reservation.adapter.api.waiting_queue.controller;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueResponse;
import com.hanghae.concert_reservation.application.waiting_queue.usecase.CreateWaitingQueueUseCase;
import com.hanghae.concert_reservation.application.waiting_queue.usecase.GetWaitingQueueUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WaitingQueue", description = "WaitingQueue API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/waiting-queue")
@RestController
public class WaitingQueueController {

    private final CreateWaitingQueueUseCase createWaitingQueueUseCase;
    private final GetWaitingQueueUseCase getWaitingQueueUseCase;

    @Operation(summary = "대기열 토큰 발급", description = "10분간 유요한 대기열 토큰을 발급합니다.")
    @PostMapping
    public ResponseEntity<WaitingQueueCreateResponse> createWaitingQueue(@RequestHeader("SESSION_ID") String sessionId) {
        return ResponseEntity.ok(createWaitingQueueUseCase.createWaitingQueue(sessionId));
    }

    /**
     * 대기열 토큰 조회
     */
    @Operation(summary = "대기열 토큰 조회", description = "대기열 토큰을 조회합니다.")
    @GetMapping
    public ResponseEntity<WaitingQueueResponse> getWaitingQueue(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid
    ) {
        return ResponseEntity.ok(getWaitingQueueUseCase.getWaitingQueue(waitingQueueUuid));
    }
}
