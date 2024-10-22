package com.hanghae.concert_reservation.infrastructure.payment.repository;

import com.hanghae.concert_reservation.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
