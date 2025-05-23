package com.hanghae.concert_reservation.infrastructure.jpa.concert.repository;

import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    @Query("SELECT cs FROM ConcertSeat cs WHERE cs.concertScheduleId = :concertScheduleId")
    List<ConcertSeat> getConcertSeats(@Param("concertScheduleId") Long concertScheduleId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT cs FROM ConcertSeat cs WHERE cs.id = :concertSeatId")
    Optional<ConcertSeat> findByIdWithOptimisticLock(@Param("concertSeatId") Long concertSeatId);
}
