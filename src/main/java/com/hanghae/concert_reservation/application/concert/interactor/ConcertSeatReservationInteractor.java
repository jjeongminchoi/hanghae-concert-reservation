package com.hanghae.concert_reservation.application.concert.interactor;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.ReservationResponse;
import com.hanghae.concert_reservation.domain.concert.dto.command.ConcertSeatReservationCommand;
import com.hanghae.concert_reservation.application.concert.usecase.ConcertSeatReservationUseCase;
import com.hanghae.concert_reservation.domain.concert.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class ConcertSeatReservationInteractor implements ConcertSeatReservationUseCase {

    private final ConcertService concertService;
    private final RedissonClient redissonClient;

    @Override
    public ReservationResponse reservation(ConcertSeatReservationCommand command) {
        RLock lock = redissonClient.getLock("seatLock:" + command.seatId());
        System.out.println("lock:" + lock.isLocked());
        boolean isLocked = false;

        try {
            isLocked = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (isLocked) {
                return concertService.reservation(command);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }

        return null;
    }
}
