package com.hanghae.concert_reservation.infrastructure.jpa.waiting_queue.repository;

import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.waiting_queue.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
public class RedisQueueRepositoryImpl implements WaitingQueueRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waiting_queue";
    private static final String ACTIVE_QUEUE_KEY = "active_queue";

    public String addToWaitingQueue(String sessionId) {
        // 대기열에 저장하기 전에 기존 세션 ID로 생성한 항목이 있는지 먼저 확인
        Boolean exists = redisTemplate.opsForHash().hasKey(WAITING_QUEUE_KEY, sessionId);

        if (!exists) {
            String queueToken = UUID.randomUUID().toString();

            // 세션 ID로 생성된 항목이 없으면 대기열 저장
            redisTemplate.opsForHash().put(WAITING_QUEUE_KEY, sessionId, sessionId);
            redisTemplate.opsForZSet().add(WAITING_QUEUE_KEY, queueToken, System.currentTimeMillis());

            return queueToken;
        }
        return null;
    }

    public void moveToActiveQueue() {
        Set<String> itemsToMove = redisTemplate.opsForZSet().range(WAITING_QUEUE_KEY, 0, 100);

        for (String item : itemsToMove) {
            // 대기열에서 제거
            redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, item);

            // 활성열에 추가 및 TTL 설정
            long ttl = 900000;
            redisTemplate.opsForZSet().add(ACTIVE_QUEUE_KEY, item, System.currentTimeMillis() + ttl);
            redisTemplate.expire(ACTIVE_QUEUE_KEY, ttl, TimeUnit.MILLISECONDS);
        }
    }

    public void existsActiveWaitingQueue(String waitingQueueUuid) {
        Double score = redisTemplate.opsForZSet().score(ACTIVE_QUEUE_KEY, waitingQueueUuid);
        if (score == null) {
            throw new BizNotFoundException("활성열 상태가 아닙니다.");
        }
    }
}
