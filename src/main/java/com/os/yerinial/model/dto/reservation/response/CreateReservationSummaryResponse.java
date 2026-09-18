package com.os.yerinial.model.dto.reservation.response;

import java.math.BigDecimal;

public record CreateReservationSummaryResponse(
        Long eventId,
        Long customerId,
        String eventName,
        BigDecimal totalPrice,
        int ticketCount
) {
}
