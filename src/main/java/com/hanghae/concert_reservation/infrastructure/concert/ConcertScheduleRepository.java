package com.hanghae.concert_reservation.infrastructure.concert;

import com.hanghae.concert_reservation.domain.concert.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertScheduleRepository extends JpaRepository<ConcertSchedule, Long> {

    @Query("SELECT cs FROM ConcertSchedule cs WHERE cs.concertId = :concertId AND cs.concertScheduleStatus = :concertScheduleStatus")
    List<ConcertSchedule> getConcertSchedules(@Param("concertId") Long concertId, @Param("concertScheduleStatus") String concertScheduleStatus);
}
