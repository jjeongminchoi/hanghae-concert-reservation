package com.hanghae.concert_reservation.domain.waiting_queue.service;

import com.hanghae.concert_reservation.config.exception.BizAlreadyExistsException;
import com.hanghae.concert_reservation.config.exception.BizInvalidException;
import com.hanghae.concert_reservation.config.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.waiting_queue.WaitingQueue;
import com.hanghae.concert_reservation.usecase.waiting_queue.WaitingQueueUseCase;
import com.hanghae.concert_reservation.infrastructure.waiting_queue.WaitingQueueRepository;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WaitingQueueService implements WaitingQueueUseCase {

    private final WaitingQueueRepository waitingQueueRepository;

    @Transactional
    @Override
    public WaitingQueueCreateResponse createWaitingQueue(String sessionId) {
        waitingQueueRepository.findWaitingQueueBySessionId(sessionId)
                .orElseThrow(() -> new BizAlreadyExistsException("대기열이 이미 존재합니다"));

        WaitingQueue waitingQueue = waitingQueueRepository.save(WaitingQueue.from(sessionId, UUID.randomUUID().toString()));
        return new WaitingQueueCreateResponse(waitingQueue.getWaitingQueueUuid());
    }

    @Override
    public WaitingQueueResponse getWaitingQueue(String waitingQueueUuid) {
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueue(waitingQueueUuid);

        if (waitingQueue == null) throw new BizNotFoundException("대기열이 존재하지 않습니다");

        return new WaitingQueueResponse(
                waitingQueue.getId(),
                waitingQueue.getSessionId(),
                waitingQueue.getWaitingQueueStatus(),
                waitingQueue.getCreateAt(),
                waitingQueue.getExpiredAt(),
                waitingQueue.getUpdatedAt()
        );
    }

    @Override
    public void existsActiveWaitingQueue(String waitingQueueUuid) {
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueWithActive(waitingQueueUuid);
        if (waitingQueue == null) throw new BizInvalidException("대기열이 유효하지 않습니다");
    }
}
