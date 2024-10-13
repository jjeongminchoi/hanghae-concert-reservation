package com.hanghae.concert_reservation.interfaces.api.concert.response;

import java.time.LocalDateTime;

public record ConcertDateResponse (
        Long concertDateId,
        Long concertId,
        LocalDateTime date,
        String venue
) {
}
