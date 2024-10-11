package com.hanghae.concert_reservation.interfaces.api.queue.response;

import com.hanghae.concert_reservation.domain.queue.constant.QueueStatus;

import java.time.LocalDateTime;

public record GetQueueResponse (
        Long id,
        Long userId,
        QueueStatus status,
        LocalDateTime createdAt,
        LocalDateTime enteredAt,
        LocalDateTime expiredAt
) {}
