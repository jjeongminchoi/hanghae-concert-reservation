package com.hanghae.concert_reservation.domain.payment.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleDataPlatformListener implements DataPlatformListener {

    private final DataPlatformService dataPlatformService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void sendPaymentInfo(PaymentInfoEvent infoEvent) {
        try {
            log.info("데이터 플랫폼 전송: {}", infoEvent);
            dataPlatformService.service(infoEvent);
        } catch (Exception e) {
            log.error("데이터 플랫폼 에러 발생: {}", e.getMessage());
        }
    }
}
