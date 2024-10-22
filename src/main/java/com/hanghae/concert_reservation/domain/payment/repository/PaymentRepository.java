package com.hanghae.concert_reservation.domain.payment.repository;

import com.hanghae.concert_reservation.domain.payment.entity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
