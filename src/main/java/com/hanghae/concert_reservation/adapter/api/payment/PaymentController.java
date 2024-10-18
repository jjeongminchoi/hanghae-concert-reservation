package com.hanghae.concert_reservation.adapter.api.payment;

import com.hanghae.concert_reservation.adapter.api.payment.request.PaymentCommand;
import com.hanghae.concert_reservation.adapter.api.payment.response.PaymentResponse;
import com.hanghae.concert_reservation.usecase.payment.PaymentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "Payment API")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    @Operation(summary = "예약 결제", description = "임시 예약을 최종 결제합니다.")
    @PostMapping("/api/v1/payment")
    public ResponseEntity<PaymentResponse> payment(
            @RequestHeader("WAITING-QUEUE-UUID") String waitingQueueUuid,
            @RequestBody PaymentCommand command
    ) {
        paymentUseCase.payment(waitingQueueUuid, command);
        return ResponseEntity.ok(new PaymentResponse(1000L));
    }
}
