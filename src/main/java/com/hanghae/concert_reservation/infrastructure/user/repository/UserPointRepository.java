package com.hanghae.concert_reservation.infrastructure.user.repository;

import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {

    UserPoint getPointByUserId(Long userId);
}
