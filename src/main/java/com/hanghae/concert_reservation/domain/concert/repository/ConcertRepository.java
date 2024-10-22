package com.hanghae.concert_reservation.domain.concert.repository;

import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.dto.ReservationInfoDto;

import java.util.List;

public interface ConcertRepository {

    List<ConcertSchedule> getConcertSchedules(Long concertId, String concertScheduleStatus);
    List<ConcertSeat> getConcertSeats(Long concertScheduleId);
    ConcertSeat getConcertSeat(Long concertSeatId);
    ReservationInfoDto getReservationInfo(Long concertSeatId);
    List<Reservation> getExpiredTempReservations();
    Reservation save(Reservation reservation);
    Reservation existReservedReservation(Long reservationId);
}
