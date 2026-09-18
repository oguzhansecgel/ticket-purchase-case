package com.os.yerinial.service;

import com.os.yerinial.exception.*;
import com.os.yerinial.metrics.MetricsOutcome;
import com.os.yerinial.metrics.ReservationMetricOperation;
import com.os.yerinial.metrics.ReservationMetrics;
import com.os.yerinial.model.dto.reservation.request.CreateReservationRequest;
import com.os.yerinial.model.dto.reservation.response.CreateReservationSummaryResponse;
import com.os.yerinial.model.entity.*;
import com.os.yerinial.model.repository.CustomerRepository;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.ReservationRepository;
import io.micrometer.core.instrument.binder.http.Outcome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationService {

    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMetrics reservationMetrics;
    private final PaymentService paymentService;
    public ReservationService(EventRepository eventRepository,
                              CustomerRepository customerRepository,
                              ReservationRepository reservationRepository,
                              ReservationMetrics reservationMetrics,
                              PaymentService paymentService) {
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMetrics = reservationMetrics;
        this.paymentService = paymentService;
    }

    @Transactional
    public CreateReservationSummaryResponse createReservation(CreateReservationRequest request) {
        Customer existingCustomer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("customer not found id: " + request.customerId()));

        Event existingEvent = eventRepository.findByIdForUpdate(request.eventId())
                .orElseThrow(() -> new NotFoundException("event not found id: " + request.eventId()));

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

        BigDecimal totalPrice = existingEvent.getPrice().multiply(BigDecimal.valueOf(request.ticketCount()));
        Reservation createdReservation = new Reservation();
        createdReservation.setCustomer(existingCustomer);
        createdReservation.setEvent(existingEvent);
        createdReservation.setTicketCount(request.ticketCount());
        createdReservation.setTotalPrice(totalPrice);
        createdReservation.setStatus(ReservationStatus.CONFIRMED);

        reservationRepository.save(createdReservation);
        reservationMetrics.reservationOperationIncrement(ReservationMetricOperation.CREATE, MetricsOutcome.SUCCESS);
        try {

        } catch (Exception e) {

        }
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

        if(reservation.getEvent().getEventDate().isBefore(Instant.now().plus(23, ChronoUnit.HOURS).plus(59, ChronoUnit.MINUTES))) {
            throw new ReservationCancellationTooLateException("Reservation not cancelled last 24 hours");
        }

        if (reservation.getEvent().getAvailableCapacity() == 0) {
            Event event = reservation.getEvent();
            event.setStatus(EventStatus.ACTIVE);
        }

        if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
            Event event = reservation.getEvent();
            event.setAvailableCapacity(event.getAvailableCapacity() + reservation.getTicketCount());
        }
        reservationMetrics.reservationOperationIncrement(ReservationMetricOperation.CANCEL, MetricsOutcome.SUCCESS);
        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
