package com.os.yerinial.controller;

import com.os.yerinial.model.dto.reservation.request.CreateReservationRequest;
import com.os.yerinial.model.dto.reservation.response.CreateReservationSummaryResponse;
import com.os.yerinial.service.ReservationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private static final String API_URL = "/api/v1/reservation";

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public CreateReservationSummaryResponse post(@Valid @RequestBody CreateReservationRequest request) {
        logger.info(API_URL + "/create incoming request -> {}", request);
        return reservationService.createReservation(request);
    }

    @PatchMapping("/cancelled/{reservationId}")
    public void cancelledReservation(@PathVariable("reservationId") Long reservationId) {
        logger.info(API_URL + "/cancelled/{reservationId} incoming request -> {}", reservationId);
        reservationService.cancelReservation(reservationId);
    }
}
