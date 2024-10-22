package com.hanghae.concert_reservation.domain.concert.service;

import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import com.hanghae.concert_reservation.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ReservationScheduleService {

    private final ConcertRepository concertRepository;

    /**
     * 임시 예약 만료 스케줄
     * 5분마다 5분안에 결제 되지 않은 임시 예약을 체크해 좌석과 예약의 상태를 만료시킨다.
     */
    public void checkTemporaryReservationExpiration() {
        List<Reservation> expiredTempReservations = concertRepository.getExpiredTempReservations();

        // 예약 정보 만료
        expiredTempReservations.forEach(
                reservation -> reservation.changeReservationStatus(ReservationStatus.EXPIRED)
        );

        // 좌석 활성 상태로 변경
        expiredTempReservations.forEach(
                reservation -> concertRepository.getConcertSeat(reservation.getConcertSeatId()).changeConcertSeatStatus(ConcertSeatStatus.AVAILABLE)
        );
    }
}
