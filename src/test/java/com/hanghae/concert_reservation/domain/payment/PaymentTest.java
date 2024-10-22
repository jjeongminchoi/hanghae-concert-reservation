package com.hanghae.concert_reservation.domain.payment;

import com.hanghae.concert_reservation.config.exception.BizIllegalArgumentException;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    @Test
    void shouldFailWhenUsingPointsWithInsufficientBalance() {
        // given
        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(10000));
        BigDecimal useAmount = BigDecimal.valueOf(10001);

        // exception
        assertThatThrownBy(() -> userPoint.usePoint(useAmount))
                .isInstanceOf(BizIllegalArgumentException.class)
                .hasMessageContaining("잔액이 부족합니다");
    }

    @Test
    void shouldFailWhenUsingNegativePointAmount() {
        // given
        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(10000));
        BigDecimal useAmount = BigDecimal.valueOf(-10000);

        // exception
        assertThatThrownBy(() -> userPoint.usePoint(useAmount))
                .isInstanceOf(BizInvalidException.class)
                .hasMessageContaining("비정상 결제 금액입니다");
    }
}