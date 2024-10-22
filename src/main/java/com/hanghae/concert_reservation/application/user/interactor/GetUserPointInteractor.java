package com.hanghae.concert_reservation.application.user.interactor;

import com.hanghae.concert_reservation.adapter.api.user.dto.response.UserPointResponse;
import com.hanghae.concert_reservation.application.user.usecase.GetUserPointUseCase;
import com.hanghae.concert_reservation.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetUserPointInteractor implements GetUserPointUseCase {

    private final UserService userService;

    @Override
    public UserPointResponse getUserPoints(Long userId) {
        return userService.getUserPoints(userId);
    }
}
