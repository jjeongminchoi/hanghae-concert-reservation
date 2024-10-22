package com.hanghae.concert_reservation.application.user.usecase;

import com.hanghae.concert_reservation.domain.user.dto.command.UserPointChargeCommand;

public interface UserPointChargeUseCase {

    void userPointCharge(UserPointChargeCommand command);
}
