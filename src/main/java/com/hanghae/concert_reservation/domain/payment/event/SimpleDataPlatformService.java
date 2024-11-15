package com.hanghae.concert_reservation.domain.payment.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleDataPlatformService implements DataPlatformService {

    @Override
    public void service(PaymentInfoEvent infoEvent) {
        try {
            log.info("데이터 플랫폼 서비스중...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("데이터 플랫폼 서비스중 error...");
        }
    }
}
