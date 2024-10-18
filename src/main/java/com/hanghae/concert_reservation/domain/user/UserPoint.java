package com.hanghae.concert_reservation.domain.user;

import com.hanghae.concert_reservation.config.exception.BizIllegalArgumentException;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private UserPoint(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static UserPoint of(Long userId, BigDecimal balance) {
        return new UserPoint(userId, balance);
    }

    public void chargePoint(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizIllegalArgumentException("충전할 금액이 없습니다");
        }
        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now();
    }

    public void usePoint(BigDecimal amount) {
        if (balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new BizIllegalArgumentException("잔액이 부족합니다");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizInvalidException("비정상 결제 금액입니다");
        }
        this.balance = this.balance.subtract(amount);
        this.updatedAt = LocalDateTime.now();
    }
}
