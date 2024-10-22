package com.hanghae.concert_reservation.common.exception;

import org.springframework.http.HttpStatus;

public class BizNotFoundException extends BizException {

    private final int STATUS_CODE = HttpStatus.NOT_FOUND.value();

    public BizNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}
