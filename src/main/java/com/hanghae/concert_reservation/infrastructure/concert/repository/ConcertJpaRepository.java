package com.hanghae.concert_reservation.infrastructure.concert.repository;

import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
}
