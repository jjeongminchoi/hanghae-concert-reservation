package com.hanghae.concert_reservation.infrastructure.concert;

import com.hanghae.concert_reservation.domain.concert.ConcertSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat, Long> {

    @Query("SELECT cs FROM ConcertSeat cs WHERE cs.concertScheduleId = :concertScheduleId")
    List<ConcertSeat> getConcertSeats(@Param("concertScheduleId") Long concertScheduleId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT cs FROM ConcertSeat cs WHERE cs.id = :concertSeatId")
    ConcertSeat getConcertSeat(@Param("concertSeatId") Long concertSeatId);
}
