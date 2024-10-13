package com.hanghae.concert_reservation.interfaces.api.user;

import com.hanghae.concert_reservation.interfaces.api.user.request.UserPointCharge;
import com.hanghae.concert_reservation.interfaces.api.user.response.UserPointResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    /**
     * 유저 포인트 충전
     */
    @PostMapping("/api/v1/users/point")
    public ResponseEntity<Integer> pointCharge(@RequestBody UserPointCharge request) {
        return ResponseEntity.ok().build();
    }


    /**
     * 유저 포인트 조회
     */
    @GetMapping("/api/v1/users/{userId}/point")
    public ResponseEntity<UserPointResponse> getUserPoint(@PathVariable Long userId) {
        return ResponseEntity.ok(new UserPointResponse(1L, 10000));
    }
}
