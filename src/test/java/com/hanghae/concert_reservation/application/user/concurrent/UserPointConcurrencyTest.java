package com.hanghae.concert_reservation.application.user.concurrent;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import com.hanghae.concert_reservation.domain.user.dto.command.UserPointChargeCommand;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.domain.user.service.UserService;
import com.hanghae.concert_reservation.infrastructure.user.repository.UserPointJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserPointConcurrencyTest {

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void tearDown() {
        userPointJpaRepository.deleteAll();
    }

    @Test
    void shouldHandleConcurrentPointCharging() throws InterruptedException {
        // given
        UserPoint userPoint = userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(0)));
        UserPointChargeCommand command = new UserPointChargeCommand(userPoint.getUserId(), BigDecimal.valueOf(10000), UserPointTransactionType.CHARGE);

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    userService.userPointCharge(command);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        UserPoint res = userPointJpaRepository.findById(userPoint.getId()).get();
        assertThat(res.getBalance().longValue()).isEqualTo(BigDecimal.valueOf(100000).longValue());
    }
}
