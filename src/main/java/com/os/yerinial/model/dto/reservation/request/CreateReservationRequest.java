package com.os.yerinial.model.dto.reservation.request;

public record CreateReservationRequest(
        Long customerId,
        Long eventId,
        int ticketCount
) {
}
