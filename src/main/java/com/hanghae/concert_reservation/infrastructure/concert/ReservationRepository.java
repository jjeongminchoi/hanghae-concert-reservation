package com.hanghae.concert_reservation.infrastructure.concert;

import com.hanghae.concert_reservation.domain.concert.Reservation;
import com.hanghae.concert_reservation.infrastructure.concert.response.ReservationInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(
            "SELECT new com.hanghae.concert_reservation.infrastructure.concert.response.ReservationInfoDto(c.name, cs.date, se.price) " +
            "FROM Concert c " +
            "JOIN ConcertSchedule cs ON c.id = cs.concertId " +
            "JOIN ConcertSeat se ON cs.id = se.concertScheduleId " +
            "WHERE se.id = :concertSeatId " +
            "AND se.concertSeatStatus = 'AVAILABLE'"
    )
    ReservationInfoDto getReservationInfo(@Param("concertSeatId") Long concertSeatId);

    @Query("SELECT r FROM Reservation r WHERE r.reservationStatus = 'RESERVED' AND r.tempReservedAt < CURRENT_TIMESTAMP")
    List<Reservation> findExpiredTempReservations();
}
