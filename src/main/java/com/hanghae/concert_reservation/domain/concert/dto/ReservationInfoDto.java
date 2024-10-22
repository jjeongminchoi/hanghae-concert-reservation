package com.hanghae.concert_reservation.domain.concert.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationInfoDto(
        String concertName,
        LocalDateTime concertDate,
        BigDecimal price
) {
}
