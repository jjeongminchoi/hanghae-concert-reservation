package com.hanghae.concert_reservation.common.exception.handler;

import com.hanghae.concert_reservation.common.exception.BizException;
import com.hanghae.concert_reservation.common.exception.response.ErrorApiResponse;
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
