package com.hanghae.concert_reservation.domain.concert.service;

import com.hanghae.concert_reservation.adapter.api.concert.request.ReservationCommand;
import com.hanghae.concert_reservation.adapter.api.concert.response.*;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import com.hanghae.concert_reservation.domain.concert.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertScheduleStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.infrastructure.concert.ConcertScheduleRepository;
import com.hanghae.concert_reservation.infrastructure.concert.ConcertSeatRepository;
import com.hanghae.concert_reservation.infrastructure.concert.ReservationRepository;
import com.hanghae.concert_reservation.infrastructure.concert.response.ReservationInfoDto;
import com.hanghae.concert_reservation.usecase.concert.ConcertUseCase;
import com.hanghae.concert_reservation.usecase.waiting_queue.WaitingQueueUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ConcertService implements ConcertUseCase {

    private final WaitingQueueUseCase waitingQueueUseCase;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public ConcertSchedulesResponse getConcertSchedules(String waitingQueueUuid, Long concertId, ConcertScheduleStatus concertScheduleStatus) {
        // 대기열 유효성 체크
        waitingQueueUseCase.existsActiveWaitingQueue(waitingQueueUuid);

        // 예약 가능한 콘서트 일정 조회
        List<ConcertScheduleResponse> scheduleResponses = concertScheduleRepository.getConcertSchedules(concertId, concertScheduleStatus.toString())
                .stream()
                .map(concertSchedule -> new ConcertScheduleResponse(
                        concertSchedule.getId(),
                        concertSchedule.getConcertId(),
                        concertSchedule.getDate(),
                        concertSchedule.getVenue()
                )).toList();

        return new ConcertSchedulesResponse(scheduleResponses);
    }

    @Override
    public ConcertSeatsResponse getConcertSeats(String waitingQueueUuid, Long concertId, Long concertScheduleId) {
        // 대기열 유효성 체크
        waitingQueueUseCase.existsActiveWaitingQueue(waitingQueueUuid);

        // 콘서트 좌석 조회
        List<ConcertSeatResponse> seatResponses = concertSeatRepository.getConcertSeats(concertScheduleId)
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
    @Override
    public ReservationResponse reservation(String waitingQueueUuid, ReservationCommand command) {
        // 대기열 유효성 체크
        waitingQueueUseCase.existsActiveWaitingQueue(waitingQueueUuid);

        // 콘서트 좌석
        ConcertSeat concertSeat = concertSeatRepository.getConcertSeat(command.getConcertSeatId());
        if (concertSeat.getConcertSeatStatus() != ConcertSeatStatus.AVAILABLE) {
            throw new BizInvalidException("예약할 수 없는 좌석입니다");
        }

        // 콘서트 예약
        ReservationInfoDto reservationInfo = reservationRepository.getReservationInfo(concertSeat.getId());
        Reservation reservation = reservationRepository.save(Reservation.of(command.getUserId(), concertSeat.getId(), reservationInfo.getConcertName(), reservationInfo.getConcertDate(), reservationInfo.getPrice()));
        concertSeat.changeConcertSeatStatus(ConcertSeatStatus.TEMPORARILY_RESERVED);
        reservation.setToTemporaryReservationTime();
        return new ReservationResponse(reservation.getId());
    }
}
