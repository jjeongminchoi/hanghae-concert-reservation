package com.hanghae.concert_reservation.config.exception.response;

import lombok.Getter;

@Getter
public class ErrorApiResponse {

    private final String message;

    public ErrorApiResponse(String message) {
        this.message = message;
    }
}
