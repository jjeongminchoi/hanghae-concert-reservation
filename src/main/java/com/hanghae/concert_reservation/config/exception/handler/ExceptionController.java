package com.hanghae.concert_reservation.config.exception.handler;

import com.hanghae.concert_reservation.config.exception.BizException;
import com.hanghae.concert_reservation.config.exception.response.ErrorApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorApiResponse> waitingQueueExceptionHandler(BizException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorApiResponse(e.getMessage()));
    }
}
