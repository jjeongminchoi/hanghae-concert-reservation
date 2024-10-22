package com.hanghae.concert_reservation.infrastructure.user.repository;

import com.hanghae.concert_reservation.domain.user.entity.UserPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointHistoryJpaRepository extends JpaRepository<UserPointHistory, Long> {
}
