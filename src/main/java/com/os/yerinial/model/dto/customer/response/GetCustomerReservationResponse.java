package com.os.yerinial.model.dto.customer.response;

import com.os.yerinial.model.entity.ReservationStatus;

import java.math.BigDecimal;

public record GetCustomerReservationResponse(
        Long reservationId,
        Long eventId,
        Long customerId,
        String eventName,
        BigDecimal totalPrice,
        int ticketCount,
        ReservationStatus status
) {
}
