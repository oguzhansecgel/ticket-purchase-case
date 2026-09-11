package com.os.yerinial.model.dto.event.response;

import com.os.yerinial.model.entity.EventStatus;
import com.os.yerinial.model.entity.Venue;

import java.time.Instant;

public record GetEventDetails(
        Long evetId,
        String description,
        Long venueId,
        Instant eventDate,
        double price,
        int totalCapacity,
        int availableCapacity,
        EventStatus eventStatus,
        boolean isActive
) {
}
