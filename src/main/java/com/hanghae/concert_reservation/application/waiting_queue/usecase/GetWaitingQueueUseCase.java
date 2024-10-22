package com.hanghae.concert_reservation.application.waiting_queue.usecase;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueResponse;

public interface GetWaitingQueueUseCase {

    WaitingQueueResponse getWaitingQueue(String waitingQueueUuid);
}
