package com.hanghae.concert_reservation.application.user.interactor;

import com.hanghae.concert_reservation.adapter.api.user.dto.response.UserPointResponse;
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
class GetUserPointInteractorTest {

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private GetUserPointInteractor getUserPointInteractor;

    @Test
    void getUserPoint() {
        // given
        userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(0)));

        // when
        UserPointResponse userPoint = getUserPointInteractor.getUserPoints(1L);

        // then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.balance()).isEqualTo(BigDecimal.valueOf(0));
    }
}