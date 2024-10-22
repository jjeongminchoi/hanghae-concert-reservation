package com.hanghae.concert_reservation.application.payment.interactor;

import com.hanghae.concert_reservation.common.exception.BizIllegalArgumentException;
import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.repository.ConcertRepository;
import com.hanghae.concert_reservation.domain.payment.dto.command.PaymentCommand;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.user.repository.UserPointJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ConcertPaymentInteractorTest {

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

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
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from(sessionId, uuid)).activateWaitingQueue();
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule schedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(schedule.getId(), 1, BigDecimal.valueOf(100000)));
        Reservation reservation = concertRepository.save(Reservation.of(1L, concertSeat.getId(), concert.getName(), schedule.getDate(), concertSeat.getPrice()));
        reservation.setToTemporaryReservationTime();
        userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(100000)));

        PaymentCommand command = new PaymentCommand(1L, reservation.getId());

        // when
        concertPaymentInteractor.payment(uuid, command);

        // then
    }

    @Test
    void should_ThrowException_When_payment_NotFound_Reservation() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from(sessionId, uuid)).activateWaitingQueue();
        PaymentCommand command = new PaymentCommand(1L, 1L);

        // exception
        assertThatThrownBy(() -> concertPaymentInteractor.payment(uuid, command))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("예약을 찾을 수 없습니다.");
    }

    @Test
    void should_ThrowException_When_payment_NotFound_UserPoint() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from(sessionId, uuid)).activateWaitingQueue();
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule schedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(schedule.getId(), 1, BigDecimal.valueOf(100000)));
        Reservation reservation = concertRepository.save(Reservation.of(1L, concertSeat.getId(), concert.getName(), schedule.getDate(), concertSeat.getPrice()));
        reservation.setToTemporaryReservationTime();

        PaymentCommand command = new PaymentCommand(1L, reservation.getId());

        // exception
        assertThatThrownBy(() -> concertPaymentInteractor.payment(uuid, command))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("유저 포인트가 없습니다.");
    }

    @Test
    void should_ThrowException_When_payment_InsufficientBalance() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from(sessionId, uuid)).activateWaitingQueue();
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule schedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(schedule.getId(), 1, BigDecimal.valueOf(100000)));
        Reservation reservation = concertRepository.save(Reservation.of(1L, concertSeat.getId(), concert.getName(), schedule.getDate(), concertSeat.getPrice()));
        reservation.setToTemporaryReservationTime();
        userPointJpaRepository.save(UserPoint.of(1L, BigDecimal.valueOf(0)));

        PaymentCommand command = new PaymentCommand(1L, reservation.getId());

        // exception
        assertThatThrownBy(() -> concertPaymentInteractor.payment(uuid, command))
                .isInstanceOf(BizIllegalArgumentException.class)
                .hasMessageContaining("잔액이 부족합니다");
    }
}