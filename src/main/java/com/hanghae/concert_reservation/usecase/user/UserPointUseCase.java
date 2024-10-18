package com.hanghae.concert_reservation.usecase.user;

import com.hanghae.concert_reservation.adapter.api.user.request.UserPointChargeCommand;
import com.hanghae.concert_reservation.adapter.api.user.response.UserPointResponse;

import java.math.BigDecimal;

public interface UserPointUseCase {

    void userPointCharge(UserPointChargeCommand command);

    void userPointUse(Long userId, BigDecimal amount);

    UserPointResponse getUserPoints(Long userId);
}
