package com.os.yerinial.model.dto.customer.response;

import com.os.yerinial.model.entity.ReservationStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record GetCustomerReservationResponse(
        Long reservationId,
        Long eventId,
        Long customerId,
        String eventName,
        Instant eventDate,
        String venueName,
        String city,
        BigDecimal totalPrice,
        int ticketCount,
        ReservationStatus status
) {
}
