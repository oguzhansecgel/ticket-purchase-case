package com.os.yerinial.service;

import com.os.yerinial.exception.NotFoundException;
import com.os.yerinial.model.dto.event.request.CreateEventRequest;
import com.os.yerinial.model.dto.event.response.GetEventById;
import com.os.yerinial.model.dto.event.response.GetEventDetails;
import com.os.yerinial.model.dto.event.response.GetEventResponse;
import com.os.yerinial.model.entity.Event;
import com.os.yerinial.model.entity.EventStatus;
import com.os.yerinial.model.entity.Venue;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.VenueRepository;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<GetEventResponse> getEventList(String city, Instant eventDateStart, Instant eventDateEnd, EventStatus status, int pageNumber, int pageSize) {
        String upperCityName = city != null ? city.substring(0, 1).toUpperCase(Locale.ROOT) + city.substring(1) : city;
        PageRequest of = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<Event> events = eventRepository.findAll(createSpecification(upperCityName, eventDateStart, eventDateEnd, status), of);
        return events.stream().map(event -> new GetEventResponse(
                        event.getId(),
                        event.getName(),
                        event.getEventDate(),
                        event.getVenue().getName(),
                        event.getEventImageUrl(),
                        event.getVenue().getImageUrl(),
                        event.getVenue().getCity(),
                        event.getPrice(),
                        event.getAvailableCapacity(),
                        event.getStatus()

                ))
                .toList();
    }

    public void createEvent(CreateEventRequest request) {
        Venue venue = venueRepository.findById(request.venueId()).orElseThrow(() -> new NotFoundException("venue not found id: " + request.venueId()));
        Event event = new Event();
        event.setName(request.name());
        event.setDescription(request.description());
        event.setEventImageUrl(request.eventImageUrl());
        event.setEventDate(request.eventDate());
        event.setDescription(request.eventImageUrl());
        event.setTotalCapacity(request.totalCapacity());
        event.setAvailableCapacity(request.totalCapacity());
        event.setPrice(request.price());
        event.setStatus(EventStatus.ACTIVE);
        event.setVenue(venue);

        eventRepository.save(event);
    }

    public GetEventDetails getEventDetailsById(long eventId) {
        return eventRepository.findActiveEventById(eventId).orElseThrow(() -> new NotFoundException("event not found id: " + eventId));
    }

    public Specification<Event> createSpecification(
            String city,
            Instant startDate,
            Instant endDate,
            EventStatus status
    ) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (city != null) {
                predicates.add(
                        cb.equal(root.get("venue").get("city"), city)
                );
            }

            if (status != null) {
                predicates.add(
                        cb.equal(root.get("status"), status)
                );
            }

            if (startDate != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("eventDate"), startDate
                        )
                );
            }

            if (endDate != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("eventDate"), endDate
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Transactional
    public void expiredEventDateDays(int expirationDay) {
        List<GetEventById> eventList = eventRepository.findByEventDateBefore(Instant.now().minus(expirationDay, ChronoUnit.DAYS));
        for(GetEventById event: eventList) {
            Event savedEvent = eventRepository.findById(event.id()).orElseThrow(() -> new NotFoundException("Event not found id: " + event.id()));
            savedEvent.setActive(false);
            eventRepository.save(savedEvent);
            logger.info("isActive false event id: " + event.id());
        }
    }

    @Transactional
    public void expiredEventDate() {
        List<GetEventDetails> getEventDetails =  eventRepository.findEventByIdAndActiveAndStatus();
        for(GetEventDetails eventDetails: getEventDetails) {
            Event savedEvent = eventRepository.findById(eventDetails.evetId()).orElseThrow(() -> new NotFoundException("Event not found id: " + eventDetails.evetId()));
            savedEvent.setStatus(EventStatus.COMPLETED);
            eventRepository.save(savedEvent);
        }
    }
}
