package com.os.yerinial.model.dto.customer.response;

import com.os.yerinial.model.entity.ReservationStatus;

public record GetCustomerReservationResponse(
        Long reservationId,
        Long eventId,
        Long customerId,
        String eventName,
        double totalPrice,
        int ticketCount,
        ReservationStatus status
) {
}
