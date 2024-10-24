package com.hanghae.concert_reservation.domain.waiting_queue.service;

import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueResponse;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    @Transactional
    public WaitingQueueCreateResponse createWaitingQueue(String sessionId) {
        waitingQueueRepository.existWaitingQueueBySessionId(sessionId);

        WaitingQueue waitingQueue = waitingQueueRepository.save(WaitingQueue.from(sessionId, UUID.randomUUID().toString()));
        return new WaitingQueueCreateResponse(waitingQueue.getWaitingQueueUuid());
    }

    public WaitingQueueResponse getWaitingQueue(String sessionId) {
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueue(sessionId);

        return new WaitingQueueResponse(
                waitingQueue.getId(),
                waitingQueue.getSessionId(),
                waitingQueue.getWaitingQueueStatus(),
                waitingQueue.getCreateAt(),
                waitingQueue.getExpiredAt(),
                waitingQueue.getUpdatedAt()
        );
    }

    public void existsActiveWaitingQueue(String waitingQueueUuid) {
        waitingQueueRepository.existsActiveWaitingQueue(waitingQueueUuid);
    }
}
