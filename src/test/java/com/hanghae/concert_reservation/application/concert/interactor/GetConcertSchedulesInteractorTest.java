package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ConcertSchedulesResponse;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class GetConcertSchedulesInteractorTest {

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private GetConcertSchedulesInteractor getConcertSchedulesInteractor;

    @Test
    void getConcertSchedules() {
        // given
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from("sessionId", uuid)).activateWaitingQueue();
        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        concertScheduleJpaRepository.saveAll(
                IntStream.range(0, 10)
                        .mapToObj(i -> ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀")).toList()
        );

        // when
        ConcertSchedulesResponse result =
                getConcertSchedulesInteractor.getConcertSchedules(uuid, concert.getId(), ConcertScheduleStatus.OPEN);

        // then
        assertThat(result.concerts().size()).isEqualTo(10);
        assertThat(result.concerts().get(0).venue()).isEqualTo("콘서트홀");
    }
}