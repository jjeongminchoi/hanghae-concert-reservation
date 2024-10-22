package com.hanghae.concert_reservation.infrastructure.payment.repository;

import com.hanghae.concert_reservation.domain.payment.entity.Payment;
import com.hanghae.concert_reservation.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
}
