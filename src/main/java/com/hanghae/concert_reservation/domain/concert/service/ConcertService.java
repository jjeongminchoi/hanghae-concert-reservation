package com.hanghae.concert_reservation.domain.concert.service;

import com.hanghae.concert_reservation.adapter.api.concert.dto.response.*;
import com.hanghae.concert_reservation.application.concert.dto.command.ConcertSeatReservationCommand;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.waiting_queue.service.WaitingQueueService;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ReservationJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.response.ReservationInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ConcertService {

    private final WaitingQueueService waitingQueueService;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;

    public ConcertSchedulesResponse getConcertSchedules(String waitingQueueUuid, Long concertId, ConcertScheduleStatus concertScheduleStatus) {
        // 대기열 유효성 체크
        waitingQueueService.existsActiveWaitingQueue(waitingQueueUuid);

        // 예약 가능한 콘서트 일정 조회
        List<ConcertScheduleResponse> scheduleResponses = concertScheduleJpaRepository.getConcertSchedules(concertId, concertScheduleStatus.toString())
                .stream()
                .map(concertSchedule -> new ConcertScheduleResponse(
                        concertSchedule.getId(),
                        concertSchedule.getConcertId(),
                        concertSchedule.getDate(),
                        concertSchedule.getVenue()
                )).toList();

        return new ConcertSchedulesResponse(scheduleResponses);
    }

    public ConcertSeatsResponse getConcertSeats(String waitingQueueUuid, Long concertId, Long concertScheduleId) {
        // 대기열 유효성 체크
        waitingQueueService.existsActiveWaitingQueue(waitingQueueUuid);

        // 콘서트 좌석 조회
        List<ConcertSeatResponse> seatResponses = concertSeatJpaRepository.getConcertSeats(concertScheduleId)
                .stream()
                .map(concertSeat -> new ConcertSeatResponse(
                        concertSeat.getId(),
                        concertSeat.getSeatNumber(),
                        concertSeat.getPrice(),
                        concertSeat.getConcertSeatStatus()
                )).toList();

        List<ConcertSeatResponse> availableSeats = seatResponses.stream()
                .filter(seat -> seat.concertSeatStatus() == ConcertSeatStatus.AVAILABLE).toList();
        List<ConcertSeatResponse> unAvailableSeats = seatResponses.stream()
                .filter(seat -> seat.concertSeatStatus() == ConcertSeatStatus.TEMPORARILY_RESERVED
                        || seat.concertSeatStatus() == ConcertSeatStatus.RESERVED).toList();

        return new ConcertSeatsResponse(availableSeats, unAvailableSeats);
    }

    @Transactional
    public ReservationResponse reservation(String waitingQueueUuid, ConcertSeatReservationCommand command) {
        // 대기열 유효성 체크
        waitingQueueService.existsActiveWaitingQueue(waitingQueueUuid);

        // 콘서트 좌석
        ConcertSeat concertSeat = concertSeatJpaRepository.getConcertSeat(command.seatId());
        if (concertSeat.getConcertSeatStatus() != ConcertSeatStatus.AVAILABLE) {
            throw new BizInvalidException("예약할 수 없는 좌석입니다");
        }

        // 콘서트 예약
        ReservationInfoDto reservationInfo = reservationJpaRepository.getReservationInfo(concertSeat.getId());
        Reservation reservation = reservationJpaRepository.save(Reservation.of(command.userId(), concertSeat.getId(), reservationInfo.getConcertName(), reservationInfo.getConcertDate(), reservationInfo.getPrice()));
        concertSeat.changeConcertSeatStatus(ConcertSeatStatus.TEMPORARILY_RESERVED);
        reservation.setToTemporaryReservationTime();
        return new ReservationResponse(reservation.getId());
    }
}
