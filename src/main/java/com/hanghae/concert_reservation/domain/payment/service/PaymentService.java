package com.hanghae.concert_reservation.domain.payment.service;

import com.hanghae.concert_reservation.adapter.api.payment.request.PaymentCommand;
import com.hanghae.concert_reservation.adapter.api.payment.response.PaymentResponse;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import com.hanghae.concert_reservation.config.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.concert.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import com.hanghae.concert_reservation.domain.payment.Payment;
import com.hanghae.concert_reservation.infrastructure.concert.ConcertSeatRepository;
import com.hanghae.concert_reservation.infrastructure.concert.ReservationRepository;
import com.hanghae.concert_reservation.infrastructure.payment.PaymentRepository;
import com.hanghae.concert_reservation.usecase.payment.PaymentUseCase;
import com.hanghae.concert_reservation.usecase.user.UserPointUseCase;
import com.hanghae.concert_reservation.usecase.waiting_queue.WaitingQueueUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final WaitingQueueUseCase waitingQueueUseCase;
    private final UserPointUseCase userPointUseCase;
    private final ReservationRepository reservationRepository;
    private final ConcertSeatRepository concertSeatRepository;

    @Override
    public PaymentResponse payment(String waitingQueueUuid, PaymentCommand command) {
        // 대기열 유효성 체크
        waitingQueueUseCase.existsActiveWaitingQueue(waitingQueueUuid);

        // 예약건 확인
        Reservation reservation = reservationRepository.findById(command.getReservationId())
                .orElseThrow(() -> new BizNotFoundException("예약을 찾을 수 없습니다"));

        if (reservation.getReservationStatus() != ReservationStatus.RESERVED) {
            throw new BizInvalidException("예약할 수 없습니다.");
        }

        // 포인트 사용
        userPointUseCase.userPointUse(command.getUserId(), reservation.getPrice());

        // 결제
        Payment payment = paymentRepository.save(paymentRepository.save(Payment.of(command.getUserId(), command.getReservationId())));
        reservation.changeReservationStatus(ReservationStatus.PAYMENT);
        concertSeatRepository.getConcertSeat(reservation.getConcertSeatId()).changeConcertSeatStatus(ConcertSeatStatus.RESERVED);

        return new PaymentResponse(payment.getId());
    }
}
