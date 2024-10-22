package com.hanghae.concert_reservation.common.exception;

import org.springframework.http.HttpStatus;

public class BizAlreadyExistsException extends BizException {

    private final int STATUS_CODE = HttpStatus.CONFLICT.value();

    public BizAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}
