package com.hanghae.concert_reservation.application.user.interactor;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import com.hanghae.concert_reservation.domain.user.dto.command.UserPointChargeCommand;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.infrastructure.user.repository.UserPointJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class UserPointChargeInteractorTest {

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private UserPointChargeInteractor userPointChargeInteractor;

    @Test
    void userPointCharge() {
        // given
        UserPoint userPoint = userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(0)));
        UserPointChargeCommand command = new UserPointChargeCommand(userPoint.getId(), BigDecimal.valueOf(10000), UserPointTransactionType.CHARGE);

        // when
        userPointChargeInteractor.userPointCharge(command);

        // then
        assertThat(userPoint.getBalance()).isEqualTo(BigDecimal.valueOf(10000));
    }
}