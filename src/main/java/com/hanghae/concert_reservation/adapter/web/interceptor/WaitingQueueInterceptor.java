package com.hanghae.concert_reservation.adapter.web.interceptor;

import com.hanghae.concert_reservation.common.exception.BizNotFoundException;
import com.hanghae.concert_reservation.domain.waiting_queue.service.WaitingQueueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class WaitingQueueInterceptor implements HandlerInterceptor {

    private final WaitingQueueService waitingQueueService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 헤더에서 대기열 토큰 추출
        String waitingQueueUuid = request.getHeader("WAITING-QUEUE-UUID");

        // 토큰 유무 확인
        if (waitingQueueUuid == null) throw new BizNotFoundException("대기열 토큰이 없습니다");

        // 토큰 검증
        waitingQueueService.existsActiveWaitingQueue(waitingQueueUuid);

        return true;
    }
}
