package com.hanghae.concert_reservation.domain.user.service;

import com.hanghae.concert_reservation.adapter.api.user.dto.response.UserPointResponse;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.domain.user.entity.UserPointHistory;
import com.hanghae.concert_reservation.domain.user.constant.UserPointTransactionType;
import com.hanghae.concert_reservation.domain.user.repository.UserRepository;
import com.hanghae.concert_reservation.domain.user.dto.command.UserPointChargeCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void userPointCharge(UserPointChargeCommand command) {
        // 유저 포인트 충전
        UserPoint userPoint = userRepository.getPointByUserId(command.userId());
        userPoint.chargePoint(command.amount());

        // 유저 포인트 내역 저장
        userRepository.save(UserPointHistory.of(userPoint.getId(), command.userPointTransactionType(), command.amount()));
    }

    @Transactional
    public void userPointUse(Long userId, BigDecimal amount) {
        // 유저 포인트 사용
        UserPoint userPoint = userRepository.getPointByUserId(userId);
        userPoint.usePoint(amount);

        // 유저 포인트 내역 저장
        userRepository.save(UserPointHistory.of(userPoint.getId(), UserPointTransactionType.PAYMENT, amount));
    }

    public UserPointResponse getUserPoints(Long userId) {
        UserPoint userPoint = userRepository.getPointByUserId(userId);
        return new UserPointResponse(userPoint.getUserId(), userPoint.getBalance());
    }
}
