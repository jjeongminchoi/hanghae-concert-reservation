package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ReservationResponse;
import com.hanghae.concert_reservation.common.exception.BizInvalidException;
import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.concert.dto.command.ConcertSeatReservationCommand;
import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ConcertSeatReservationInteractorTest {

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private ConcertSeatReservationInteractor concertSeatReservationInteractor;

    @Test
    void concertReservation() {
        // given
        String uuid = UUID.randomUUID().toString();

        waitingQueueRepository.save(WaitingQueue.from("sessionId", uuid)).activateWaitingQueue();
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(concertSchedule.getId(), 1, BigDecimal.valueOf(100000)));

        ConcertSeatReservationCommand command = new ConcertSeatReservationCommand(1L, concert.getId(), concertSchedule.getId(), concertSeat.getId());

        // when
        ReservationResponse result = concertSeatReservationInteractor.reservation(uuid, command);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void should_ThrowException_When_NotFound_WaitingQueue() {
        // given
        String uuid = UUID.randomUUID().toString();
        ConcertSeatReservationCommand command = new ConcertSeatReservationCommand(1L, 1L, 1L, 1L);

        // exception
        assertThatThrownBy(() -> concertSeatReservationInteractor.reservation(uuid, command))
                .isInstanceOf(BizInvalidException.class)
                .hasMessageContaining("대기열이 유효하지 않습니다");
    }

    @Test
    void should_ThrowException_When_NotFound_ConcertSeat() {
        // given
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from("sessionId", uuid)).activateWaitingQueue();
        ConcertSeatReservationCommand command = new ConcertSeatReservationCommand(1L, 1L, 1L, 1L);

        // exception
        assertThatThrownBy(() -> concertSeatReservationInteractor.reservation(uuid, command))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("콘서트 좌석을 찾을 수 없습니다.");
    }

    @Test
    void should_ThrowException_When_NotFound_ConcertReservationInfo() {
        // given
        String uuid = UUID.randomUUID().toString();

        waitingQueueRepository.save(WaitingQueue.from("sessionId", uuid)).activateWaitingQueue();
        ConcertSeat concertSeat = concertSeatJpaRepository.save(ConcertSeat.of(1L, 1, BigDecimal.valueOf(100000)));

        ConcertSeatReservationCommand command = new ConcertSeatReservationCommand(1L, 1L, 1L, concertSeat.getId());

        // exception
        assertThatThrownBy(() -> concertSeatReservationInteractor.reservation(uuid, command))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("콘서트 예약 정보를 찾지 못했습니다");
    }
}