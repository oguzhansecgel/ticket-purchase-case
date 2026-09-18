package com.os.yerinial.model.dto.event.request;

import com.os.yerinial.model.entity.EventStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateEventRequest(
        @NotBlank(message = "eventName must not be null")
        String name,
        String description,
        String eventImageUrl,
        @NotNull(message = "eventDate must not be null")
        @FutureOrPresent
        Instant eventDate,
        @Positive
        BigDecimal price,
        @Positive(message = "totalCapacity must be not negative or equals zero")
        int totalCapacity,
        @NotNull(message = "venue must be not be null")
        Long venueId
) {
}
