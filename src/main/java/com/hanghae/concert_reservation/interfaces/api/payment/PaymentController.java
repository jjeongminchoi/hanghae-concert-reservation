package com.hanghae.concert_reservation.interfaces.api.payment;

import com.hanghae.concert_reservation.interfaces.api.payment.request.PaymentRequest;
import com.hanghae.concert_reservation.interfaces.api.payment.response.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    /**
     * 결제
     */
    @PostMapping("/api/v1/payment")
    public ResponseEntity<PaymentResponse> payment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(new PaymentResponse(1000L));
    }
}
