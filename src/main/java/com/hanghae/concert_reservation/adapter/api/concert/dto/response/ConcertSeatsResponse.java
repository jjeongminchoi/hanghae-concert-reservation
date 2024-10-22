package com.hanghae.concert_reservation.adapter.api.concert.dto.response;

import java.util.List;

public record ConcertSeatsResponse(
        List<ConcertSeatResponse> availableSeats,
        List<ConcertSeatResponse> unAvailableSeats
) {
}
