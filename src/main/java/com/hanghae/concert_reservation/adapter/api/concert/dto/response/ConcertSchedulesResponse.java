package com.hanghae.concert_reservation.adapter.api.concert.dto.response;

import java.util.List;

public record ConcertSchedulesResponse(
        List<ConcertScheduleResponse> concerts
) {
}
