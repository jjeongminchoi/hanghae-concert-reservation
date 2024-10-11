package com.hanghae.concert_reservation.interfaces.api.queue.response;

import java.time.LocalDateTime;

public record CreateQueueResponse (
        Long id,
        String token,
        LocalDateTime createdAt
) {}
