package com.hanghae.concert_reservation.application.payment.interactor;

import com.hanghae.concert_reservation.adapter.api.payment.dto.response.PaymentResponse;
import com.hanghae.concert_reservation.common.exception.BizIllegalArgumentException;
import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.repository.ConcertRepository;
import com.hanghae.concert_reservation.domain.payment.dto.command.PaymentCommand;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.user.repository.UserPointJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ConcertPaymentInteractorTest {

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private ConcertPaymentInteractor concertPaymentInteractor;

    @Test
    void payment_success() {
        // given
        UserPoint userPoint = userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(100000)));
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule schedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(schedule.getId(), 1, BigDecimal.valueOf(100000)));
        Reservation reservation = concertRepository.save(Reservation.of(userPoint.getUserId(), concertSeat.getId(), concert.getName(), schedule.getDate(), concertSeat.getPrice()));
        reservation.setToTemporaryReservationTime();

        PaymentCommand command = new PaymentCommand(userPoint.getUserId(), reservation.getId());

        // when
        PaymentResponse result = concertPaymentInteractor.payment(command);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void should_ThrowException_When_payment_NotFound_Reservation() {
        // given
        PaymentCommand command = new PaymentCommand(1L, 1L);

        // exception
        assertThatThrownBy(() -> concertPaymentInteractor.payment(command))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("예약을 찾을 수 없습니다.");
    }

    @Test
    void should_ThrowException_When_payment_NotFound_UserPoint() {
        // given
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule schedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(schedule.getId(), 1, BigDecimal.valueOf(100000)));
        Reservation reservation = concertRepository.save(Reservation.of(1L, concertSeat.getId(), concert.getName(), schedule.getDate(), concertSeat.getPrice()));
        reservation.setToTemporaryReservationTime();

        PaymentCommand command = new PaymentCommand(1L, reservation.getId());

        // exception
        assertThatThrownBy(() -> concertPaymentInteractor.payment(command))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("유저 포인트가 없습니다.");
    }

    @Test
    void should_ThrowException_When_payment_InsufficientBalance() {
        // given
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule schedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(schedule.getId(), 1, BigDecimal.valueOf(100000)));
        Reservation reservation = concertRepository.save(Reservation.of(1L, concertSeat.getId(), concert.getName(), schedule.getDate(), concertSeat.getPrice()));
        reservation.setToTemporaryReservationTime();
        userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(0)));

        PaymentCommand command = new PaymentCommand(1L, reservation.getId());

        // exception
        assertThatThrownBy(() -> concertPaymentInteractor.payment(command))
                .isInstanceOf(BizIllegalArgumentException.class)
                .hasMessageContaining("잔액이 부족합니다");
    }
}