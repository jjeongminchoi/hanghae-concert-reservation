package com.hanghae.concert_reservation.application.waiting_queue.interactor;

import com.hanghae.concert_reservation.adapter.api.waiting_queue.dto.response.WaitingQueueCreateResponse;
import com.hanghae.concert_reservation.common.exception.BizAlreadyExistsException;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CreateWaitingQueueInteractorTest {

    @Autowired
    private WaitingQueueRepository waitingQueueRepository;

    @Autowired
    private CreateWaitingQueueInteractor createWaitingQueueInteractor;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void createWaitingQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

        // when
        WaitingQueueCreateResponse result = createWaitingQueueInteractor.createWaitingQueue(sessionId);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void should_ThrowException_When_already_exist_WaitingQueue() {
        // given
        String sessionId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        waitingQueueRepository.addToWaitingQueue(sessionId);

        // exception
        assertThatThrownBy(() -> createWaitingQueueInteractor.createWaitingQueue(sessionId))
                .isInstanceOf(BizAlreadyExistsException.class)
                .hasMessageContaining("대기열이 이미 존재합니다");
    }
}