package com.hanghae.concert_reservation.infrastructure.jpa.user.repository;

import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT up FROM UserPoint up WHERE up.userId = :userId")
    Optional<UserPoint> findByUserId(@Param("userId") Long userId);
}
