package com.hanghae.concert_reservation.domain.user.entity;

import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "user_point_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserPointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long pointId;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserPointTransactionType userPointTransactionType;

    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private UserPointHistory(Long pointId, UserPointTransactionType userPointTransactionType, BigDecimal amount) {
        this.pointId = pointId;
        this.userPointTransactionType = userPointTransactionType;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public static UserPointHistory of(Long pointId, UserPointTransactionType userPointTransactionType, BigDecimal amount) {
        return new UserPointHistory(pointId, userPointTransactionType, amount);
    }
}
