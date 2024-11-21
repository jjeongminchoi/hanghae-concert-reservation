package com.hanghae.concert_reservation.infrastructure.jpa.payment.repository;

import com.hanghae.concert_reservation.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
