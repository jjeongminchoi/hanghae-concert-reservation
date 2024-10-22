package com.hanghae.concert_reservation.infrastructure.concert.repository;

import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    @Query("SELECT cs FROM ConcertSeat cs WHERE cs.concertScheduleId = :concertScheduleId")
    List<ConcertSeat> getConcertSeats(@Param("concertScheduleId") Long concertScheduleId);
}
