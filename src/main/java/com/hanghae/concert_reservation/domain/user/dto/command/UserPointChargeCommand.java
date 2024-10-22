package com.hanghae.concert_reservation.domain.user.dto.command;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;

import java.math.BigDecimal;

public record UserPointChargeCommand(
        Long userId,
        BigDecimal amount,
        UserPointTransactionType userPointTransactionType
) {
}
