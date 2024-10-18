package com.hanghae.concert_reservation.adapter.api.user.request;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserPointChargeCommand {

    private Long userId;

    private BigDecimal amount;

    private UserPointTransactionType userPointTransactionType;

    public UserPointChargeCommand(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
        this.userPointTransactionType = UserPointTransactionType.CHARGE;
    }
}
