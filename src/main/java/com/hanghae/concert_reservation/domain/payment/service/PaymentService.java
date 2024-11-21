package com.hanghae.concert_reservation.domain.payment.service;

import com.hanghae.concert_reservation.adapter.api.payment.dto.response.PaymentResponse;
import com.hanghae.concert_reservation.domain.concert.dto.ReservationInfoDto;
import com.hanghae.concert_reservation.domain.concert.repository.ConcertRepository;
import com.hanghae.concert_reservation.domain.payment.dto.command.PaymentCommand;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.concert.constant.ConcertSeatStatus;
import com.hanghae.concert_reservation.domain.concert.constant.ReservationStatus;
import com.hanghae.concert_reservation.domain.payment.entity.Payment;
import com.hanghae.concert_reservation.domain.payment.event.PaymentEventPublisher;
import com.hanghae.concert_reservation.domain.payment.event.PaymentInfoEvent;
import com.hanghae.concert_reservation.domain.payment.repository.PaymentRepository;
import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.domain.user.entity.UserPointHistory;
import com.hanghae.concert_reservation.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final ConcertRepository concertRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    @Transactional
    public PaymentResponse payment(PaymentCommand command) {
        // 예약 확인
        Reservation reservation = concertRepository.existReservedReservation(command.reservationId());

        // 포인트 사용
        UserPoint userPoint = userRepository.getPointByUserId(command.userId());
        userPoint.usePoint(reservation.getPrice());

        // 포인트 사용 내역 저장
        userRepository.save(UserPointHistory.of(userPoint.getId(), UserPointTransactionType.PAYMENT, reservation.getPrice()));

        // 결제
        Payment payment = paymentRepository.save(Payment.of(command.userId(), command.reservationId()));
        reservation.changeReservationStatus(ReservationStatus.PAYMENT);
        concertRepository.getConcertSeat(reservation.getConcertSeatId()).changeConcertSeatStatus(ConcertSeatStatus.RESERVED);

        ReservationInfoDto reservationInfo = concertRepository.getReservationInfo(reservation.getConcertSeatId());
        paymentEventPublisher.publish(new PaymentInfoEvent(reservationInfo.concertName(), reservation.getConcertSeatId(), reservation.getPrice()));

        log.info("[PaymentInfo] userId: {}, concertName: {}, concertDate: {}, concertSeatId: {}, price: {}",
                command.userId(), reservation.getConcertName(), reservation.getConcertDate(), reservation.getConcertSeatId(), reservation.getPrice());
        return new PaymentResponse(payment.getId());
    }
}
