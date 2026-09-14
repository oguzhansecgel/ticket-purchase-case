package com.os.yerinial.model.dto.reservation.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateReservationRequest(
        @NotNull
        Long customerId,
        @NotNull
        Long eventId,
        @Max(10)
        @Positive
        int ticketCount
) {
}
