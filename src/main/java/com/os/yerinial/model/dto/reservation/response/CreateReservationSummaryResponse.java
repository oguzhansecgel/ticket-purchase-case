package com.os.yerinial.model.dto.reservation.response;

public record CreateReservationSummaryResponse(
        Long eventId,
        Long customerId,
        String eventName,
        double totalPrice,
        int ticketCount
) {
}
