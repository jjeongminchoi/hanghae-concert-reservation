package com.hanghae.concert_reservation.interfaces.api.waiting_queue.response;

import com.hanghae.concert_reservation.domain.waiting_queue.constant.WaitingQueueStatus;

import java.time.LocalDateTime;

public record GetWaitingQueueResponse(
        Long id,
        Long userId,
        Long concertId,
        WaitingQueueStatus status,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {}
