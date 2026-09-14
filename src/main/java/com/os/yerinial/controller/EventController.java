package com.os.yerinial.controller;

import com.os.yerinial.model.dto.event.request.CreateEventRequest;
import com.os.yerinial.model.dto.event.response.GetEventDetails;
import com.os.yerinial.model.dto.event.response.GetEventResponse;
import com.os.yerinial.model.entity.EventStatus;
import com.os.yerinial.service.EventService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    private final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    private static final String API_URL = "/api/v1/event/";

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public void createEvent(@Valid @RequestBody CreateEventRequest eventRequest) {
        logger.info(API_URL + "create incoming request -> {}", eventRequest);
        eventService.createEvent(eventRequest);
    }

    @GetMapping
    public List<GetEventResponse> getEvent(@RequestParam(required = false) String city,
                                           @RequestParam(required = false) Instant eventStartDate,
                                           @RequestParam(required = false) Instant eventEndDate,
                                           @RequestParam(required = false) EventStatus status,
                                           @RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "20") int pageSize) {
        logger.info(API_URL + "/{city}/{eventStartDate}/{eventEndDate}/{status} incoming request -> " +
                "city: {}, startDate: {}, endDate: {}, status: {}", city, eventStartDate, eventEndDate, status);
        return eventService.getEventList(city, eventStartDate, eventEndDate, status, pageNumber, pageSize);
    }

    @GetMapping("/details/{eventId}")
    public GetEventDetails getEventDetailsById(@PathVariable("eventId") Long eventId) {
        logger.info(API_URL + "/details/{eventId} incoming request -> {}", eventId);
        return eventService.getEventDetailsById(eventId);
    }

    @GetMapping("/check")
    public void check() {
        eventService.expiredEventDateDays(2);
    }
}
