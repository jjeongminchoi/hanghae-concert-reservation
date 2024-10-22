package com.hanghae.concert_reservation.adapter.api.user.dto.request;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import com.hanghae.concert_reservation.domain.user.dto.command.UserPointChargeCommand;

import java.math.BigDecimal;

public record UserPointChargeRequest(
        BigDecimal amount
) {

    public UserPointChargeCommand toCommand(Long userId) {
        return new UserPointChargeCommand(userId, amount, UserPointTransactionType.CHARGE);
    }
}
