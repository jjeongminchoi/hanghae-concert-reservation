package com.hanghae.concert_reservation.domain.waiting_queue.service;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    public WaitingQueueCreateResponse createWaitingQueue(String sessionId) {
        String queueToken = waitingQueueRepository.addToWaitingQueue(sessionId);
        return new WaitingQueueCreateResponse(queueToken);
    }

    public void existsActiveWaitingQueue(String queueToken) {
        waitingQueueRepository.existsActiveWaitingQueue(queueToken);
    }
}
