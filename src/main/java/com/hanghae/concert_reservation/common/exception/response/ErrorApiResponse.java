package com.hanghae.concert_reservation.common.exception.response;

import lombok.Getter;

@Getter
public class ErrorApiResponse {

    private final String message;

    public ErrorApiResponse(String message) {
        this.message = message;
    }
}
