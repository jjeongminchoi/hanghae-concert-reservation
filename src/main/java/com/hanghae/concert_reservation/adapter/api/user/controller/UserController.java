package com.hanghae.concert_reservation.adapter.api.user.controller;

import com.hanghae.concert_reservation.adapter.api.user.dto.request.UserPointChargeRequest;
import com.hanghae.concert_reservation.adapter.api.user.dto.response.UserPointResponse;
import com.hanghae.concert_reservation.application.user.usecase.GetUserPointUseCase;
import com.hanghae.concert_reservation.application.user.usecase.UserPointChargeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{userId}/points")
@RestController
public class UserController {

    private final UserPointChargeUseCase userPointChargeUseCase;
    private final GetUserPointUseCase getUserPointUseCase;

    @Operation(summary = "유저 포인트 충전", description = "유저의 포인트를 충전합니다.")
    @PostMapping
    public ResponseEntity<Integer> chargeUserPoints(
            @PathVariable Long userId,
            @RequestBody UserPointChargeRequest request
    ) {
        userPointChargeUseCase.userPointCharge(request.toCommand(userId));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 포인트 조회", description = "유저의 포인트를 조회합니다.")
    @GetMapping
    public ResponseEntity<UserPointResponse> getUserPoints(@PathVariable Long userId) {
        return ResponseEntity.ok(getUserPointUseCase.getUserPoints(userId));
    }
}
