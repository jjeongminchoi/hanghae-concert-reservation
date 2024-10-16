package com.hanghae.concert_reservation.domain.waiting_queue;

import com.hanghae.concert_reservation.domain.waiting_queue.constant.WaitingQueueStatus;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class WaitingQueueTest {

    @Test
    void createQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();

        // when
        WaitingQueue waitingQueue = WaitingQueue.create(sessionId, uuid);

        // then
        assertThat(waitingQueue.getSessionId()).isEqualTo(sessionId);
        assertThat(waitingQueue.getWaitingQueueUuid()).isEqualTo(uuid);
        assertThat(waitingQueue.getWaitingQueueStatus()).isEqualTo(WaitingQueueStatus.WAIT);
    }

    @Test
    void activateWaitingQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();

        WaitingQueue waitingQueue = WaitingQueue.create(sessionId, uuid);

        // when
        waitingQueue.activateWaitingQueue();

        // then
        assertThat(waitingQueue.getWaitingQueueStatus()).isEqualTo(WaitingQueueStatus.ACTIVE);
    }

    @Test
    void expireWaitingQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();

        WaitingQueue waitingQueue = WaitingQueue.create(sessionId, uuid);

        // when
        waitingQueue.expireWaitingQueue();

        // then
        assertThat(waitingQueue.getWaitingQueueStatus()).isEqualTo(WaitingQueueStatus.EXPIRE);
    }
}