package com.hanghae.concert_reservation.application.user.usecase;

import com.hanghae.concert_reservation.adapter.api.user.dto.response.UserPointResponse;

public interface GetUserPointUseCase {

    UserPointResponse getUserPoints(Long userId);
}
