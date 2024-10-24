package com.hanghae.concert_reservation.application.waiting_queue.interactor;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueResponse;
import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.waiting_queue.entity.WaitingQueue;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class GetWaitingQueueInteractorTest {

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    @Autowired
    private GetWaitingQueueInteractor getWaitingQueueInteractor;

    @Test
    void getWaitingQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        String uuid = UUID.randomUUID().toString();
        waitingQueueRepository.save(WaitingQueue.from(sessionId, uuid));

        // when
        WaitingQueueResponse result = getWaitingQueueInteractor.getWaitingQueue(sessionId);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void should_ThrowException_When_NotFound_WaitingQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

        // exception
        assertThatThrownBy(() -> getWaitingQueueInteractor.getWaitingQueue(sessionId))
                .isInstanceOf(BizNotFoundException.class)
                .hasMessageContaining("대기열이 존재하지 않습니다");
    }
}