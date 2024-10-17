package com.hanghae.concert_reservation.infrastructure.user;

import com.hanghae.concert_reservation.domain.user.UserPointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointHistoryRepository extends JpaRepository<UserPointHistory, Long> {
}
