package com.os.yerinial.model.dto.event.response;

import com.os.yerinial.model.entity.EventStatus;

import java.time.Instant;

public record GetEventResponse(
        Long eventId,
        String name,
        Instant eventDate,
        String venueName,
        String eventImage,
        String venueImage,
        String city,
        double price,
        int availableCapacity,
        EventStatus status
) {
}
