package com.hanghae.concert_reservation.common.exception;

import org.springframework.http.HttpStatus;

public class BizInvalidException extends BizException {

    private final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public BizInvalidException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}
