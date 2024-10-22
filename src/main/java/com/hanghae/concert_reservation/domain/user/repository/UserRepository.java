package com.hanghae.concert_reservation.domain.user.repository;

import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.domain.user.entity.UserPointHistory;

public interface UserRepository {
    UserPoint getPointByUserId(Long userId);
    void save(UserPointHistory userPointHistory);
}
