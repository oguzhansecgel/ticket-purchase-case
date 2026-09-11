package com.os.yerinial.service;

import com.os.yerinial.exception.*;
import com.os.yerinial.model.dto.reservation.request.CreateReservationRequest;
import com.os.yerinial.model.dto.reservation.response.CreateReservationSummaryResponse;
import com.os.yerinial.model.entity.*;
import com.os.yerinial.model.repository.CustomerRepository;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ReservationService {

    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(EventRepository eventRepository,
                              CustomerRepository customerRepository,
                              ReservationRepository reservationRepository) {
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public CreateReservationSummaryResponse createReservation(CreateReservationRequest request) {
        int updatedRows = eventRepository.decreaseCapacity(request.eventId(), request.ticketCount());

        Customer existingCustomer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("customer not found id: " + request.customerId()));

        Event existingEvent = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new NotFoundException("event not found id: " + request.eventId()));

        if (!existingEvent.getStatus().equals(EventStatus.ACTIVE)) {
            throw new EventNotActiveException("event not active");
        }

        if (existingEvent.getEventDate().isBefore(Instant.now())) {
            throw new EventDateExpiredException("event date expired");
        }

        if (updatedRows == 0) {
            throw new InsufficientCapacityException("insufficient stock");
        }

        if (request.ticketCount() <= 0) {
            throw new InvalidTicketCountException("Invalid ticket count");
        }

        double totalPrice = request.ticketCount() * existingEvent.getPrice();
        Reservation createdReservation = new Reservation();
        createdReservation.setCustomer(existingCustomer);
        createdReservation.setEvent(existingEvent);
        createdReservation.setTicketCount(request.ticketCount());
        createdReservation.setTotalPrice(totalPrice);
        createdReservation.setStatus(ReservationStatus.CONFIRMED);
        int event = createdReservation.getEvent().getAvailableCapacity();
        if (event == 0) {
            existingEvent.setStatus(EventStatus.SOLD_OUT);
        }
        reservationRepository.save(createdReservation);

        return new CreateReservationSummaryResponse(existingEvent.getId(),
                existingCustomer.getId(),
                existingEvent.getName(),
                totalPrice,
                request.ticketCount()
        );
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByIdWithEvent(reservationId)
                .orElseThrow(() -> new NotFoundException("reservation not found id: " + reservationId));

        if(reservation.getEvent().getStatus() == EventStatus.COMPLETED) {
            throw new IllegalArgumentException("");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Already reservation cancelled");
        }

        //TODO: son 24 saat ya da belirlenen bir süreden az kalma durumunda iptal edilememesi.
        if (reservation.getEvent().getAvailableCapacity() == 0) {
            Event event = reservation.getEvent();
            event.setStatus(EventStatus.ACTIVE);
        }

        if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
            Event event = reservation.getEvent();
            event.setAvailableCapacity(event.getAvailableCapacity() + reservation.getTicketCount());
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
