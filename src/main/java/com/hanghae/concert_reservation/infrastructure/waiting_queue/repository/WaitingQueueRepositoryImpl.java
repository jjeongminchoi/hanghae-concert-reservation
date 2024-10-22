package com.hanghae.concert_reservation.infrastructure.waiting_queue.repository;

import com.hanghae.concert_reservation.common.exception.BizAlreadyExistsException;
import com.hanghae.concert_reservation.common.exception.BizInvalidException;
import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WaitingQueueRepositoryImpl implements WaitingQueueRepository {

    private final WaitingQueueJpaRepository waitingQueueJpaRepository;

    @Override
    public WaitingQueue getWaitingQueue(String waitingQueueUuid) {
        return waitingQueueJpaRepository.findByWaitingQueueUuid(waitingQueueUuid)
                .orElseThrow(() -> new BizNotFoundException("대기열이 존재하지 않습니다"));
    }

    @Override
    public void existsActiveWaitingQueue(String waitingQueueUuid) {
        waitingQueueJpaRepository.getActiveWaitingQueueByWaitingQueueUuid(waitingQueueUuid)
                .orElseThrow(() -> new BizInvalidException("대기열이 유효하지 않습니다"));
    }

    @Override
    public void existWaitingQueueBySessionId(String sessionId) {
        if (waitingQueueJpaRepository.findBySessionId(sessionId).isPresent()) {
            throw new BizAlreadyExistsException("대기열이 이미 존재합니다");
        }
    }

    @Override
    public List<WaitingQueue> activeWaitingQueue() {
        return waitingQueueJpaRepository.activeWaitingQueue();
    }

    @Override
    public List<WaitingQueue> expireWaitingQueue(LocalDateTime currentTime) {
        return waitingQueueJpaRepository.expireWaitingQueue(currentTime);
    }

    @Override
    public WaitingQueue save(WaitingQueue waitingQueue) {
        return waitingQueueJpaRepository.save(waitingQueue);
    }
}
