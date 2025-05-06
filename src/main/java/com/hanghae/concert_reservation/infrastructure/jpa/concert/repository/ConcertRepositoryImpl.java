package com.hanghae.concert_reservation.infrastructure.jpa.concert.repository;

import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.repository.ConcertRepository;
import com.hanghae.concert_reservation.domain.concert.dto.ReservationInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;

    /**
     * 콘서트 일정
     */
    @Override
    public List<ConcertSchedule> getConcertSchedules(Long concertId, ConcertScheduleStatus concertScheduleStatus) {
        return concertScheduleJpaRepository.getConcertSchedules(concertId, concertScheduleStatus);
    }

    /**
     * 콘서트 좌석
     */
    @Override
    public List<ConcertSeat> getConcertSeats(Long concertScheduleId) {
        return concertSeatJpaRepository.getConcertSeats(concertScheduleId);
    }

    @Override
    public ConcertSeat getConcertSeat(Long concertSeatId) {
        return concertSeatJpaRepository.findByIdWithOptimisticLock(concertSeatId)
                .orElseThrow(() -> new BizNotFoundException("콘서트 좌석을 찾을 수 없습니다."));
    }

    /**
     * 콘서트 예약
     */
    @Override
    public ReservationInfoDto getReservationInfo(Long concertSeatId) {
        return reservationJpaRepository.getReservationInfo(concertSeatId)
                .orElseThrow(() -> new BizNotFoundException("콘서트 예약 정보를 찾지 못했습니다"));
    }

    @Override
    public List<Reservation> getExpiredTempReservations() {
        return reservationJpaRepository.getExpiredTempReservations();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public Reservation existReservation(Long reservationId) {
        return reservationJpaRepository.existReservation(reservationId)
                .orElseThrow(() -> new BizNotFoundException("예약을 찾을 수 없습니다."));
    }
}
