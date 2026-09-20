package com.os.yerinial.service;

import com.os.yerinial.exception.EventDateExpiredException;
import com.os.yerinial.exception.EventNotActiveException;
import com.os.yerinial.exception.InsufficientCapacityException;
import com.os.yerinial.exception.InvalidTicketCountException;
import com.os.yerinial.exception.NotFoundException;
import com.os.yerinial.model.dto.reservation.request.CreateReservationRequest;
import com.os.yerinial.model.entity.Customer;
import com.os.yerinial.model.entity.Event;
import com.os.yerinial.model.entity.EventStatus;
import com.os.yerinial.model.entity.Reservation;
import com.os.yerinial.model.entity.ReservationStatus;
import com.os.yerinial.model.repository.CustomerRepository;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Rezervasyonun veritabani islemlerini tutar.
 * <p>
 * Bu sinif bilerek {@link ReservationService}'ten ayri duruyor. Ayni sinif icinden
 * cagrilan bir metotta {@code @Transactional} calismaz (Spring proxy devreye girmez),
 * o yuzden transaction'li metotlar ayri bir bean'de olmak zorunda.
 * <p>
 * Buradaki her metot kisa surer ve icinde hicbir dis servis cagrisi yoktur.
 * Odeme cagrisi bu sinifin disinda, transaction kapandiktan sonra yapilir.
 */
@Service
public class ReservationBookingService {

    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public ReservationBookingService(EventRepository eventRepository,
                                     CustomerRepository customerRepository,
                                     ReservationRepository reservationRepository) {
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation reserve(CreateReservationRequest request) {
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

        Reservation reservation = new Reservation();
        reservation.setCustomer(existingCustomer);
        reservation.setEvent(existingEvent);
        reservation.setTicketCount(request.ticketCount());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.PENDING_PAYMENT);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void confirm(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("reservation not found id: " + reservationId));

        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            return;
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
    }


    @Transactional
    public void release(long reservationId) {
        Reservation reservation = reservationRepository.findByIdWithEvent(reservationId)
                .orElseThrow(() -> new NotFoundException("reservation not found id: " + reservationId));

        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            return;
        }

        Event event = reservation.getEvent();
        event.setAvailableCapacity(event.getAvailableCapacity() + reservation.getTicketCount());

        if (event.getStatus() == EventStatus.SOLD_OUT && event.getAvailableCapacity() > 0) {
            event.setStatus(EventStatus.ACTIVE);
        }

        reservation.setStatus(ReservationStatus.PAYMENT_FAILED);
    }
}
