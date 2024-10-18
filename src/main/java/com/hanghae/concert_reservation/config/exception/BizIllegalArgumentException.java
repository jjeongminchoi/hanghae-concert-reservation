package com.hanghae.concert_reservation.config.exception;

import org.springframework.http.HttpStatus;

public class BizIllegalArgumentException extends BizException {

    private final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public BizIllegalArgumentException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}
