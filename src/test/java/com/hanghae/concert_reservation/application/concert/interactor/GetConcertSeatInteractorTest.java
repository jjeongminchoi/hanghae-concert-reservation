package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSeatsResponse;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class GetConcertSeatInteractorTest {

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private GetConcertSeatInteractor getConcertSeatInteractor;

    @Test
    void getConcertSeats() {
        // given
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from("sessionId", uuid)).activateWaitingQueue();
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        List<ConcertSeat> concertSeats = concertSeatJpaRepository.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> ConcertSeat.of(concertSchedule.getId(), i, BigDecimal.valueOf(100000))).toList()
        );

        concertSeats.forEach(concertSeat -> {
            if (concertSeat.getId() % 2 == 0) {
                concertSeat.changeConcertSeatStatus(ConcertSeatStatus.AVAILABLE);
            } else {
                concertSeat.changeConcertSeatStatus(ConcertSeatStatus.TEMPORARILY_RESERVED);
            }
        });

        // when
        ConcertSeatsResponse result = getConcertSeatInteractor.getConcertSeats(uuid, concert.getId(), concertSchedule.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.availableSeats().size()).isEqualTo(5);
        assertThat(result.unAvailableSeats().size()).isEqualTo(5);
    }
}