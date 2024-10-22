package com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response;

import com.hanghae.concert_reservation.domain.waiting_queue.constant.WaitingQueueStatus;

import java.time.LocalDateTime;

public record WaitingQueueResponse(
        Long id,
        String sessionId,
        WaitingQueueStatus status,
        LocalDateTime createdAt,
        LocalDateTime expiredAt,
        LocalDateTime updatedAt
) {}
