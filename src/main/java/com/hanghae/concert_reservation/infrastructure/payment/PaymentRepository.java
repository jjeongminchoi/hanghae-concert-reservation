package com.hanghae.concert_reservation.infrastructure.payment;

import com.hanghae.concert_reservation.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
