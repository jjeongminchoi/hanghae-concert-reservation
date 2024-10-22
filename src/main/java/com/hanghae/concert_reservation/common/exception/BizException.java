package com.hanghae.concert_reservation.common.exception;

public abstract class BizException extends RuntimeException {

    public abstract int getStatusCode();

    public BizException(String message) {
        super(message);
    }
}
