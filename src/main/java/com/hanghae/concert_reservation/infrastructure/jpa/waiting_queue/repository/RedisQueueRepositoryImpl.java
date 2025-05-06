package com.hanghae.concert_reservation.infrastructure.jpa.waiting_queue.repository;

import com.hanghae.concert_reservation.common.exception.BizAlreadyExistsException;
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
    private static final String SESSION_TO_TOKEN_KEY = "session_to_token";
    private static final long ACTIVE_TTL_MILLIS = 15 * 60 * 1000; // 15분 TTL

    /**
     * 대기열에 새로운 사용자 등록
     * @param sessionId 사용자 세션 ID
     * @return 새로 생성된 queueToken (UUID 기반)
     */
    public String addToWaitingQueue(String sessionId) {
        // sessionId가 이미 해시에 등록되어 있다면 예외 발생 (중복 방지)
        Boolean exists = redisTemplate.opsForHash().hasKey(SESSION_TO_TOKEN_KEY, sessionId);

        if (Boolean.TRUE.equals(exists)) {
            throw new BizAlreadyExistsException("대기열이 이미 존재합니다");
        }

        // 사용자 식별용 queueToken 생성
        String queueToken = UUID.randomUUID().toString();

        // 세션 ID로 생성된 항목이 없으면 대기열 저장 (ZSet score 는 현재 시간으로 순서 보장)
        // ZSet에 queueToken 저장 (score는 현재 시간으로 대기 순서 보장)
        redisTemplate.opsForZSet().add(WAITING_QUEUE_KEY, queueToken, System.currentTimeMillis());

        // sessionId → queueToken 매핑 저장 (중복 방지 및 추후 조회 가능)
        redisTemplate.opsForHash().put(SESSION_TO_TOKEN_KEY, sessionId, queueToken);

        return queueToken;
    }

    /**
     * 대기열에서 최대 100명을 활성열로 이동
     */
    public void moveToActiveQueue() {
        // 대기열에서 가장 최근 100명 추출 (ZSet은 자동 정렬)
        Set<String> itemsToMove = redisTemplate.opsForZSet().range(WAITING_QUEUE_KEY, 0, 100);

        if (itemsToMove == null || itemsToMove.isEmpty()) return;

        for (String item : itemsToMove) {
            // 대기열에서 제거
            redisTemplate.opsForZSet().remove(WAITING_QUEUE_KEY, item);

            // 활성열에 추가 및 TTL 설정
            long expirationTime = System.currentTimeMillis() + ACTIVE_TTL_MILLIS;
            redisTemplate.opsForZSet().add(ACTIVE_QUEUE_KEY, item, expirationTime);

        }

        // 전체 키에 TTL 부여 (선택사항: 키 전체를 만료시키고 싶을 때만)
        redisTemplate.expire(ACTIVE_QUEUE_KEY, ACTIVE_TTL_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * 활성열에 사용자가 존재하는지 확인
     * @param waitingQueueUuid queueToken
     */
    public void existsActiveWaitingQueue(String waitingQueueUuid) {
        Double score = redisTemplate.opsForZSet().score(ACTIVE_QUEUE_KEY, waitingQueueUuid);

        if (score == null) {
            throw new BizNotFoundException("활성열 상태가 아닙니다.");
        }

        // 현재 시간 기준으로 만료되었는지도 확인
        if (score < System.currentTimeMillis()) {
            // 만료된 사용자면 예외
            throw new BizNotFoundException("활성열 상태가 만료되었습니다.");
        }
    }
}
