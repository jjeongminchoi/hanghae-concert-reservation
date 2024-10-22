package com.hanghae.concert_reservation.domain.user;

import com.hanghae.concert_reservation.common.exception.BizIllegalArgumentException;
import com.hanghae.concert_reservation.common.exception.BizInvalidException;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
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
        UserPoint userPoint = UserPoint.of(userId, balance);

        // then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.getBalance()).isEqualTo(balance);
    }

    @Test
    void chargeUserPoint() {
        // given
        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(10000));
        BigDecimal chargeAmount = BigDecimal.valueOf(5000);

        // when
        userPoint.chargePoint(chargeAmount);

        // then
        assertThat(userPoint.getBalance()).isEqualTo(BigDecimal.valueOf(15000));
    }

    @Test
    void should_ThrowException_When_chargeUserPointWithNegativeAmount() {
        // given
        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(10000));
        BigDecimal chargeAmount = BigDecimal.valueOf(0);

        // exception
        assertThatThrownBy(() -> userPoint.chargePoint(chargeAmount))
                .isInstanceOf(BizIllegalArgumentException.class)
                .hasMessageContaining("충전할 금액이 없습니다");
    }

    @Test
    void should_ThrowException_When_InsufficientBalance() {
        // given
        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(10000));

        // exception
        assertThatThrownBy(() -> userPoint.usePoint(BigDecimal.valueOf(10001)))
                .isInstanceOf(BizIllegalArgumentException.class)
                .hasMessageContaining("잔액이 부족합니다");
    }

    @Test
    void should_ThrowException_When_AmountIsZeroOrNegative() {
        // given
        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(10000));

        // exception
        assertThatThrownBy(() -> userPoint.usePoint(BigDecimal.valueOf(0)))
                .isInstanceOf(BizInvalidException.class)
                .hasMessageContaining("비정상 결제 금액입니다");
    }
}