package com.hanghae.concert_reservation.adapter.api.user.dto.response;

import java.math.BigDecimal;

public record UserPointResponse(
        Long userId,
        BigDecimal balance
) {
}
