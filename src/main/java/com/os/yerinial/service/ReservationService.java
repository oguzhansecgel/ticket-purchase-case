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
        Customer existingCustomer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("customer not found id: " + request.customerId()));

        Event existingEvent = eventRepository.findByIdForUpdate(request.eventId())
                .orElseThrow(() -> new NotFoundException("event not found id: " + request.eventId()));

        if (existingEvent.getAvailableCapacity() < request.ticketCount()) {
            throw new InsufficientCapacityException("insufficient stock");
        }

        existingEvent.setAvailableCapacity(
                existingEvent.getAvailableCapacity() - request.ticketCount()
        );

        if (request.ticketCount() <= 0) {
            throw new InvalidTicketCountException("Invalid ticket count");
        }

        if (existingEvent.getStatus() != EventStatus.ACTIVE) {
            throw new EventNotActiveException("event not active");
        }

        if (existingEvent.getEventDate().isBefore(Instant.now())) {
            throw new EventDateExpiredException("event date expired");
        }

        if (existingEvent.getAvailableCapacity() < request.ticketCount()) {
            throw new InsufficientCapacityException("insufficient stock");
        }

        existingEvent.setAvailableCapacity(
                existingEvent.getAvailableCapacity() - request.ticketCount()
        );

        if (existingEvent.getAvailableCapacity() == 0) {
            existingEvent.setStatus(EventStatus.SOLD_OUT);
        }

        double totalPrice = request.ticketCount() * existingEvent.getPrice();
        Reservation createdReservation = new Reservation();
        createdReservation.setCustomer(existingCustomer);
        createdReservation.setEvent(existingEvent);
        createdReservation.setTicketCount(request.ticketCount());
        createdReservation.setTotalPrice(totalPrice);
        createdReservation.setStatus(ReservationStatus.CONFIRMED);

        reservationRepository.save(createdReservation);
        if (createdReservation.getEvent().getAvailableCapacity() == 0) {
            createdReservation.getEvent().setStatus(EventStatus.SOLD_OUT);
        }
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

        if (reservation.getEvent().getStatus() == EventStatus.COMPLETED) {
            throw new EventHasBeenCompletedException("Event has been completed");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationAlreadyCancelledException("Already reservation cancelled");
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
