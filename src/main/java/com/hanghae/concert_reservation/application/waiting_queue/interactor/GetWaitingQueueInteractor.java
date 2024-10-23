package com.hanghae.concert_reservation.application.waiting_queue.interactor;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueResponse;
import com.hanghae.concert_reservation.application.waiting_queue.usecase.GetWaitingQueueUseCase;
import com.hanghae.concert_reservation.domain.waiting_queue.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetWaitingQueueInteractor implements GetWaitingQueueUseCase {

    private final WaitingQueueService waitingQueueService;

    @Override
    public WaitingQueueResponse getWaitingQueue(String sessionId) {
        return waitingQueueService.getWaitingQueue(sessionId);
    }
}
