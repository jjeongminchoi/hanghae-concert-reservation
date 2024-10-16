package com.hanghae.concert_reservation.usecase.waiting_queue;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.adapter.api.waiting_queue.response.WaitingQueueResponse;

public interface WaitingQueueUseCase {

    WaitingQueueCreateResponse createWaitingQueue(String sessionId);

    WaitingQueueResponse getWaitingQueue(String sessionId, String waitingQueueUuid);
}
