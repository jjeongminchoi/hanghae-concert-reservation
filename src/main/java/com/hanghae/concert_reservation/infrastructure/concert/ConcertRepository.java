package com.hanghae.concert_reservation.infrastructure.concert;

import com.hanghae.concert_reservation.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    Concert getConcert();
}
