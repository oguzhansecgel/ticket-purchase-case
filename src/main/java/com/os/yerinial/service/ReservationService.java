package com.os.yerinial.service;

import com.os.yerinial.exception.*;
import com.os.yerinial.metrics.MetricsOutcome;
import com.os.yerinial.metrics.ReservationMetricOperation;
import com.os.yerinial.metrics.ReservationMetrics;
import com.os.yerinial.model.dto.payment.request.CreatePaymentRequest;
import com.os.yerinial.model.dto.reservation.request.CreateReservationRequest;
import com.os.yerinial.model.dto.reservation.response.CreateReservationSummaryResponse;
import com.os.yerinial.model.entity.*;
import com.os.yerinial.model.repository.CustomerRepository;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.ReservationRepository;
import io.micrometer.core.instrument.binder.http.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMetrics reservationMetrics;
    private final PaymentService paymentService;
    private final ReservationBookingService reservationBookingService;

    public ReservationService(EventRepository eventRepository,
                              CustomerRepository customerRepository,
                              ReservationRepository reservationRepository,
                              ReservationMetrics reservationMetrics,
                              PaymentService paymentService,
                              ReservationBookingService reservationBookingService) {
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMetrics = reservationMetrics;
        this.paymentService = paymentService;
        this.reservationBookingService = reservationBookingService;
    }

    public CreateReservationSummaryResponse createReservation(CreateReservationRequest request) {

        Reservation reservation = reservationBookingService.reserve(request);

        Customer customer = reservation.getCustomer();
        Event event = reservation.getEvent();

        try {
            paymentService.createPayment(preparePaymentRequest(request, customer, event, reservation));
        } catch (RuntimeException e) {
            reservationBookingService.release(reservation.getId());
            log.error("Odeme alinamadi, kapasite iade edildi. reservationId={}", reservation.getId(), e);
            throw e;
        }

        // 2. transaction.
        reservationBookingService.confirm(reservation.getId());
        reservationMetrics.reservationOperationIncrement(ReservationMetricOperation.CREATE, MetricsOutcome.SUCCESS);

        return new CreateReservationSummaryResponse(event.getId(),
                customer.getId(),
                event.getName(),
                reservation.getTotalPrice(),
                reservation.getTicketCount()
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

        if (reservation.getEvent().getEventDate().isBefore(Instant.now().plus(23, ChronoUnit.HOURS).plus(59, ChronoUnit.MINUTES))) {
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

    private CreatePaymentRequest preparePaymentRequest(
            CreateReservationRequest reservationRequest,
            Customer customer,
            Event event,
            Reservation reservation) {

        BigDecimal totalPrice = event.getPrice().multiply(BigDecimal.valueOf(reservation.getTicketCount()));

        var basketItems = List.of(
                new com.os.yerinial.model.dto.payment.request.CreatePaymentRequest.BasketItemRequest(
                        String.valueOf(event.getId()),
                        event.getName() + " Bileti",
                        "Etkinlik",
                        "Bilet",
                        "VIRTUAL",
                        totalPrice
                )
        );

        var buyerRequest = new com.os.yerinial.model.dto.payment.request.CreatePaymentRequest.BuyerRequest(
                String.valueOf(customer.getId()),
                customer.getName(),
                customer.getSurname(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                "11111111111",
                reservationRequest.billingAddress().address(),
                reservationRequest.ipAddress(),
                reservationRequest.billingAddress().city(),
                reservationRequest.billingAddress().country(),
                reservationRequest.billingAddress().zipCode()
        );


        return new com.os.yerinial.model.dto.payment.request.CreatePaymentRequest(
                customer.getId(),
                java.util.UUID.randomUUID().toString(),
                totalPrice,
                totalPrice,
                "TRY",
                1,
                String.valueOf(reservation.getId()),

                new com.os.yerinial.model.dto.payment.request.CreatePaymentRequest.PaymentCardRequest(
                        reservationRequest.paymentCard().cardHolderName(),
                        reservationRequest.paymentCard().cardNumber(),
                        reservationRequest.paymentCard().expireMonth(),
                        reservationRequest.paymentCard().expireYear(),
                        reservationRequest.paymentCard().cvc(),
                        reservationRequest.paymentCard().registerCard()
                ),
                buyerRequest,
                new com.os.yerinial.model.dto.payment.request.CreatePaymentRequest.AddressRequest(
                        reservationRequest.billingAddress().contactName(),
                        reservationRequest.billingAddress().city(),
                        reservationRequest.billingAddress().country(),
                        reservationRequest.billingAddress().address(),
                        reservationRequest.billingAddress().zipCode()
                ),
                new com.os.yerinial.model.dto.payment.request.CreatePaymentRequest.AddressRequest(
                        reservationRequest.billingAddress().contactName(),
                        reservationRequest.billingAddress().city(),
                        reservationRequest.billingAddress().country(),
                        reservationRequest.billingAddress().address(),
                        reservationRequest.billingAddress().zipCode()
                ),
                basketItems
        );
    }
}
