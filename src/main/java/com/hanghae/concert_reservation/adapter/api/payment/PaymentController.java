package com.hanghae.concert_reservation.adapter.api.payment;

import com.hanghae.concert_reservation.adapter.api.payment.request.PaymentCommand;
import com.hanghae.concert_reservation.adapter.api.payment.response.PaymentResponse;
import com.hanghae.concert_reservation.usecase.payment.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    /**
     * 결제
     */
    @PostMapping("/api/v1/payment")
    public ResponseEntity<PaymentResponse> payment(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @RequestBody PaymentCommand command
    ) {
        paymentUseCase.payment(waitingQueueUuid, command);
        return ResponseEntity.ok(new PaymentResponse(1000L));
    }
}
