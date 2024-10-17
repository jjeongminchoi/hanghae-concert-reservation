package com.hanghae.concert_reservation.domain.user;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class UserPointHistoryTest {

    @Test
    void createUserPointHistory() {
        // given
        Long pointId = 1L;
        UserPointTransactionType type = UserPointTransactionType.CHARGE;
        BigDecimal amount = BigDecimal.valueOf(5000);

        // when
        UserPointHistory userPointHistory = UserPointHistory.of(pointId, type, amount);

        // then
        assertThat(userPointHistory).isNotNull();
        assertThat(userPointHistory.getUserPointTransactionType()).isEqualTo(UserPointTransactionType.CHARGE);
        assertThat(userPointHistory.getAmount()).isEqualTo(amount);
    }
}