package com.hanghae.concert_reservation.domain.payment.service;

import com.hanghae.concert_reservation.adapter.api.payment.dto.response.PaymentResponse;
import com.hanghae.concert_reservation.application.payment.dto.command.PaymentCommand;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import com.hanghae.concert_reservation.config.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import com.hanghae.concert_reservation.domain.payment.entity.Payment;
import com.hanghae.concert_reservation.domain.user.service.UserService;
import com.hanghae.concert_reservation.domain.waiting_queue.service.WaitingQueueService;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.concert.repository.ReservationJpaRepository;
import com.hanghae.concert_reservation.infrastructure.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final WaitingQueueService waitingQueueService;
    private final UserService userService;
    private final PaymentRepository paymentRepository;
    private final ReservationJpaRepository reservationJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    public PaymentResponse payment(String waitingQueueUuid, PaymentCommand command) {
        // 대기열 유효성 체크
        waitingQueueService.existsActiveWaitingQueue(waitingQueueUuid);

        // 예약건 확인
        Reservation reservation = reservationJpaRepository.findById(command.reservationId())
                .orElseThrow(() -> new BizNotFoundException("예약을 찾을 수 없습니다"));

        if (reservation.getReservationStatus() != ReservationStatus.RESERVED) {
            throw new BizInvalidException("예약할 수 없습니다.");
        }

        // 포인트 사용
        userService.userPointUse(command.userId(), reservation.getPrice());

        // 결제
        Payment payment = paymentRepository.save(paymentRepository.save(Payment.of(command.userId(), command.reservationId())));
        reservation.changeReservationStatus(ReservationStatus.PAYMENT);
        concertSeatJpaRepository.getConcertSeat(reservation.getConcertSeatId()).changeConcertSeatStatus(ConcertSeatStatus.RESERVED);

        return new PaymentResponse(payment.getId());
    }
}
