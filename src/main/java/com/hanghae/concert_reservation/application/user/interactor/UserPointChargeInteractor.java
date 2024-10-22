package com.hanghae.concert_reservation.application.user.interactor;

import com.hanghae.concert_reservation.application.user.dto.command.UserPointChargeCommand;
import com.hanghae.concert_reservation.application.user.usecase.UserPointChargeUseCase;
import com.hanghae.concert_reservation.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserPointChargeInteractor implements UserPointChargeUseCase {

    private final UserService userService;

    @Override
    public void userPointCharge(UserPointChargeCommand command) {
        userService.userPointCharge(command);
    }
}
