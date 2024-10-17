package com.hanghae.concert_reservation.domain.user.service;

import com.hanghae.concert_reservation.adapter.api.user.request.UserPointChargeCommand;
import com.hanghae.concert_reservation.adapter.api.user.response.UserPointResponse;
import com.hanghae.concert_reservation.domain.user.UserPoint;
import com.hanghae.concert_reservation.domain.user.UserPointHistory;
import com.hanghae.concert_reservation.infrastructure.user.UserPointHistoryRepository;
import com.hanghae.concert_reservation.infrastructure.user.UserPointRepository;
import com.hanghae.concert_reservation.usecase.user.UserPointUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserPointService implements UserPointUseCase {

    private final UserPointRepository userPointRepository;
    private final UserPointHistoryRepository userPointHistoryRepository;

    @Transactional
    @Override
    public void userPointCharge(UserPointChargeCommand command) {
        // 유저 포인트 충전
        UserPoint userPoint = userPointRepository.getPointByUserId(command.getUserId());
        userPoint.chargePoint(command.getAmount());

        // 유저 포인트 내역 저장
        userPointHistoryRepository.save(UserPointHistory.from(userPoint.getId(), command.getUserPointTransactionType(), command.getAmount()));
    }

    @Override
    public UserPointResponse getUserPoints(Long userId) {
        UserPoint userPoint = userPointRepository.getPointByUserId(userId);
        return new UserPointResponse(userPoint.getUserId(), userPoint.getBalance());
    }
}
