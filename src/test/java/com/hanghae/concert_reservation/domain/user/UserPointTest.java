package com.hanghae.concert_reservation.domain.user;

import com.hanghae.concert_reservation.config.exception.BizIllegalArgumentException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class UserPointTest {

    @Test
    void createUserPoint() {
        // given
        Long userId = 1L;
        BigDecimal balance = BigDecimal.valueOf(10000);

        // when
        UserPoint userPoint = UserPoint.from(userId, balance);

        // then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.getBalance()).isEqualTo(balance);
    }

    @Test
    void chargeUserPoint() {
        // given
        UserPoint userPoint = UserPoint.from(1L, BigDecimal.valueOf(10000));
        BigDecimal chargeAmount = BigDecimal.valueOf(5000);

        // when
        userPoint.chargePoint(chargeAmount);

        // then
        assertThat(userPoint.getBalance()).isEqualTo(BigDecimal.valueOf(15000));
    }

    @Test
    void chargeUserPointWithNegativeAmount() {
        // given
        UserPoint userPoint = UserPoint.from(1L, BigDecimal.valueOf(10000));
        BigDecimal chargeAmount = BigDecimal.valueOf(0);

        // exception
        assertThatThrownBy(() -> userPoint.chargePoint(chargeAmount))
                .isInstanceOf(BizIllegalArgumentException.class)
                .hasMessageContaining("충전할 금액이 없습니다");
    }


}