package com.hanghae.concert_reservation.domain.concert.service;

import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableScheduling
@Transactional
@RequiredArgsConstructor
@Service
public class ReservationScheduleService {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    /**
     * 임시 예약 만료 스케줄
     * 5분마다 5분안에 결제 되지 않은 임시 예약을 체크해 좌석과 예약의 상태를 만료시킨다.
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void checkTemporaryReservationExpiration() {
        List<Reservation> expiredTempReservations = reservationJpaRepository.findExpiredTempReservations();

        // 예약 정보 만료
        expiredTempReservations.forEach(
                reservation -> reservation.changeReservationStatus(ReservationStatus.EXPIRED)
        );

        // 좌석 활성 상태로 변경
        expiredTempReservations.forEach(
                reservation -> concertSeatJpaRepository.getConcertSeat(reservation.getConcertSeatId()).changeConcertSeatStatus(ConcertSeatStatus.AVAILABLE)
        );
    }
}
