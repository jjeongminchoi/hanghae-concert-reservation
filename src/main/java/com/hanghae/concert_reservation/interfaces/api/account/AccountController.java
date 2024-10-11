package com.hanghae.concert_reservation.interfaces.api.account;

import com.hanghae.concert_reservation.interfaces.api.account.response.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    /**
     * 잔액 충전
     */
    @PostMapping("/api/v1/account/{userId}")
    public ResponseEntity<?> charge(@PathVariable Long userId) {
        return ResponseEntity.ok().build();
    }


    /**
     * 잔액 조회
     */
    @GetMapping("/api/v1/account/{userId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long userId) {
        return ResponseEntity.ok(new AccountResponse(10000));
    }
}
