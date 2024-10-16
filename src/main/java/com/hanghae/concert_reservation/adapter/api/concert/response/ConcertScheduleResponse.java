package com.hanghae.concert_reservation.adapter.api.concert.response;

import java.time.LocalDateTime;

public record ConcertScheduleResponse(
        Long concertScheduleId,
        Long concertId,
        LocalDateTime date,
        String venue
) {
}
