package com.hanghae.concert_reservation.application.concert.concurrent;

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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
        int threadCount = 11;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failedCount = new AtomicInteger(0);


        // when
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    ConcertSeatReservationCommand command = new ConcertSeatReservationCommand((long) index + 1, 1L, 1L, 1L);
                    concertService.reservation(command);
                    successCount.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                    failedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        assertThat(reservationJpaRepository.findAll().size()).isEqualTo(1);
//        assertThat(successCount.get()).isEqualTo(1);
//        assertThat(failedCount.get()).isEqualTo(threadCount - 1);
    }

    @Test
    void shouldAllowOnlyOneReservationWhenSameUserAttemptsToReserveSameSeatSimultaneously() throws InterruptedException {
        // given
        int threadCount = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failedCount = new AtomicInteger(0);

        ConcertSeatReservationCommand command = new ConcertSeatReservationCommand(1L, 1L, 1L, 1L);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    concertService.reservation(command);
                    successCount.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                    failedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        assertThat(reservationJpaRepository.findAll().size()).isEqualTo(1);
//        assertThat(successCount.get()).isEqualTo(1);
//        assertThat(failedCount.get()).isEqualTo(threadCount - 1);
    }
}
