package com.hanghae.concert_reservation.infrastructure.concert.repository;

import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.dto.ReservationInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    @Query(
            "SELECT new com.hanghae.concert_reservation.domain.concert.dto.ReservationInfoDto(c.name, cs.date, se.price) " +
            "FROM Concert c " +
            "JOIN ConcertSchedule cs ON c.id = cs.concertId " +
            "JOIN ConcertSeat se ON cs.id = se.concertScheduleId " +
            "WHERE se.id = :concertSeatId"
    )
    Optional<ReservationInfoDto> getReservationInfo(@Param("concertSeatId") Long concertSeatId);

    @Query("SELECT r FROM Reservation r WHERE r.reservationStatus = 'RESERVED' AND r.tempReservedAt < CURRENT_TIMESTAMP")
    List<Reservation> getExpiredTempReservations();

    @Query("SELECT r FROM Reservation r WHERE r.reservationStatus = 'RESERVED' AND r.tempReservedAt > CURRENT_TIMESTAMP")
    Optional<Reservation> existReservedReservation(@Param("reservationId") Long reservationId);
}
