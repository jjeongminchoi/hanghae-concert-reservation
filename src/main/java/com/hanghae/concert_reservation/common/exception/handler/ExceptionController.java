package com.hanghae.concert_reservation.common.exception.handler;

import com.hanghae.concert_reservation.common.exception.BizException;
import com.hanghae.concert_reservation.common.exception.response.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorApiResponse> BizExceptionHandler(BizException e) {
        log.warn("비즈니스 예외 발생: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorApiResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> exceptionHandler(Exception e) {
        log.error("에러 발생: {}", e.getMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorApiResponse(e.getMessage()));
    }
}
