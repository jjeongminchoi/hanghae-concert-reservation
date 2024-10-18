package com.hanghae.concert_reservation.infrastructure.concert.response;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ReservationInfoDto {

    private String concertName;

    private LocalDateTime concertDate;

    private BigDecimal price;

    public ReservationInfoDto(String concertName, LocalDateTime concertDate, BigDecimal price) {
        this.concertName = concertName;
        this.concertDate = concertDate;
        this.price = price;
    }
}
