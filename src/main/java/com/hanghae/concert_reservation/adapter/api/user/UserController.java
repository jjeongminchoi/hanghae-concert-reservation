package com.hanghae.concert_reservation.adapter.api.user;

import com.hanghae.concert_reservation.adapter.api.user.request.UserPointChargeCommand;
import com.hanghae.concert_reservation.adapter.api.user.response.UserPointResponse;
import com.hanghae.concert_reservation.usecase.user.UserPointUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserPointUseCase userPointUseCase;

    @Operation(summary = "유저 포인트 충전", description = "유저의 포인트를 충전합니다.")
    @PostMapping("/api/v1/users/points")
    public ResponseEntity<Integer> chargeUserPoints(@RequestBody UserPointChargeCommand command) {
        userPointUseCase.userPointCharge(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 포인트 조회", description = "유저의 포인트를 조회합니다.")
    @GetMapping("/api/v1/users/{userId}/points")
    public ResponseEntity<UserPointResponse> getUserPoints(@PathVariable Long userId) {
        return ResponseEntity.ok(userPointUseCase.getUserPoints(userId));
    }
}
