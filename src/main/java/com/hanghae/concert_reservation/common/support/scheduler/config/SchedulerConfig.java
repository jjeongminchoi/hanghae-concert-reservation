package com.hanghae.concert_reservation.common.support.scheduler.config;

import com.hanghae.concert_reservation.domain.concert.service.ReservationScheduleService;
import com.hanghae.concert_reservation.domain.waiting_queue.service.WaitingQueueScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@EnableScheduling
@Configuration
public class SchedulerConfig {

    private final WaitingQueueScheduleService waitingQueueScheduleService;
    private final ReservationScheduleService reservationScheduleService;

    /**
     * WaitingQueue Scheduler
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void activeWaitingQueueScheduler() {
        waitingQueueScheduleService.activeWaitingQueue();
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void expireWaitingQueueScheduler() {
        waitingQueueScheduleService.expireWaitingQueue();
    }

    /**
     * Reservation Scheduler
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void expireReservationScheduler() {
        reservationScheduleService.checkTemporaryReservationExpiration();
    }
}
