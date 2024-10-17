package com.hanghae.concert_reservation.adapter.api.user;

import com.hanghae.concert_reservation.adapter.api.user.request.UserPointChargeCommand;
import com.hanghae.concert_reservation.adapter.api.user.response.UserPointResponse;
import com.hanghae.concert_reservation.usecase.user.UserPointUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserPointUseCase userPointUseCase;

    /**
     * 유저 포인트 충전
     */
    @PostMapping("/api/v1/users/points")
    public ResponseEntity<Integer> chargeUserPoints(@RequestBody UserPointChargeCommand command) {
        userPointUseCase.userPointCharge(command);
        return ResponseEntity.ok().build();
    }

    /**
     * 유저 포인트 조회
     */
    @GetMapping("/api/v1/users/{userId}/points")
    public ResponseEntity<UserPointResponse> getUserPoints(@PathVariable Long userId) {
        return ResponseEntity.ok(userPointUseCase.getUserPoints(userId));
    }
}
