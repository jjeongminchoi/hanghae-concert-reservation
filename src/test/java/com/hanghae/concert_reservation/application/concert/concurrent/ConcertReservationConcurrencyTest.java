package com.hanghae.concert_reservation.application.concert.concurrent;

import com.hanghae.concert_reservation.application.concert.interactor.ConcertSeatReservationInteractor;
import com.hanghae.concert_reservation.config.DatabaseCleanUp;
import com.hanghae.concert_reservation.domain.concert.dto.command.ConcertSeatReservationCommand;
import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.service.ConcertService;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ReservationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class ConcertReservationConcurrencyTest {

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setUp() {
        databaseCleanUp.execute();

        Concert concert = concertJpaRepository.save(Concert.of("아이유콘서트"));
        ConcertSchedule concertSchedule = concertScheduleJpaRepository.save(ConcertSchedule.of(concert.getId(), LocalDateTime.now(), "콘서트홀"));
        concertSeatJpaRepository.save(ConcertSeat.of(concertSchedule.getId(), 1, BigDecimal.valueOf(100000)));
    }

    @Test
    void shouldAllowOnlyOneUserToReserveSeatWhenMultipleRequestsAreMadeSimultaneously() throws InterruptedException {
        // given
        int threadCount = 200;
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        CountDownLatch latch = new CountDownLatch(threadCount);

        List<Long> executionTimes = Collections.synchronizedList(new ArrayList<>());
        long startTime = System.currentTimeMillis();  // 전체 테스트 시작 시간

        // when
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.submit(() -> {
                long taskStartTime = System.currentTimeMillis();  // 작업 시작 시간
                try {
                    ConcertSeatReservationCommand command = new ConcertSeatReservationCommand((long) index + 1, 1L, 1L, 1L);
                    concertService.reservation(command);
                } finally {
                    long taskEndTime = System.currentTimeMillis();  // 작업 종료 시간
                    executionTimes.add(taskEndTime - taskStartTime);  // 작업 수행 시간 기록
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        long endTime = System.currentTimeMillis();  // 전체 테스트 종료 시간
        long totalTime = endTime - startTime;  // 전체 테스트 수행 시간

        // 통계 계산
        long minTime = executionTimes.stream().min(Long::compare).orElse(0L);  // 최소 작업 수행 시간
        long maxTime = executionTimes.stream().max(Long::compare).orElse(0L);  // 최대 작업 수행 시간
        double avgTime = executionTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);  // 평균 작업 수행 시간

        // 결과 출력
        System.out.println("전체 테스트 수행 시간: " + totalTime + "ms");
        System.out.println("최소 작업 수행 시간: " + minTime + "ms");
        System.out.println("최대 작업 수행 시간: " + maxTime + "ms");
        System.out.println("평균 작업 수행 시간: " + avgTime + "ms");

        assertThat(reservationJpaRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void shouldAllowOnlyOneReservationWhenSameUserAttemptsToReserveSameSeatSimultaneously() throws InterruptedException {
        // given
        int threadCount = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        ConcertSeatReservationCommand command = new ConcertSeatReservationCommand(1L, 1L, 1L, 1L);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    concertService.reservation(command);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        assertThat(reservationJpaRepository.findAll().size()).isEqualTo(1);
    }
}
