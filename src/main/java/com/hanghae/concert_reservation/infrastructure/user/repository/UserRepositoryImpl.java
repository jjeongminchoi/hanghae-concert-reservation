package com.hanghae.concert_reservation.infrastructure.user.repository;

import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.domain.user.entity.UserPointHistory;
import com.hanghae.concert_reservation.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPointJpaRepository userPointJpaRepository;
    private final UserPointHistoryJpaRepository userPointHistoryJpaRepository;

    @Override
    public UserPoint getPointByUserId(Long userId) {
        return userPointJpaRepository.findById(userId)
                .orElseThrow(() -> new BizNotFoundException("유저 포인트가 없습니다."));
    }

    @Override
    public void save(UserPointHistory userPointHistory) {
        userPointHistoryJpaRepository.save(userPointHistory);
    }
}
