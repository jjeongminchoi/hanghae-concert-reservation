package com.hanghae.concert_reservation.usecase.user;

import com.hanghae.concert_reservation.adapter.api.user.request.UserPointChargeCommand;
import com.hanghae.concert_reservation.adapter.api.user.response.UserPointResponse;

public interface UserPointUseCase {

    void userPointCharge(UserPointChargeCommand command);

    UserPointResponse getUserPoints(Long userId);
}
