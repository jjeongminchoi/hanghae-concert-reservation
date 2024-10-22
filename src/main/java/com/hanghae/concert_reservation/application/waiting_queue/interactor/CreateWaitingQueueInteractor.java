package com.hanghae.concert_reservation.application.waiting_queue.interactor;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.application.waiting_queue.usecase.CreateWaitingQueueUseCase;
import com.hanghae.concert_reservation.domain.waiting_queue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateWaitingQueueInteractor implements CreateWaitingQueueUseCase {

    private final WaitingQueueService waitingQueueService;

    @Override
    public WaitingQueueCreateResponse createWaitingQueue(String sessionId) {
        return waitingQueueService.createWaitingQueue(sessionId);
    }
}
