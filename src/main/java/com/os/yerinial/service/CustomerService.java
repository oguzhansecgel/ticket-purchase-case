package com.os.yerinial.service;

import com.os.yerinial.exception.BusinessException;
import com.os.yerinial.model.dto.customer.response.GetCustomerReservationResponse;
import com.os.yerinial.model.entity.ReservationStatus;
import com.os.yerinial.model.repository.CustomerRepository;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.ReservationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class CustomerService {

    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public CustomerService(EventRepository eventRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    @Cacheable(value = "customer-reservations", key = "#customerId + ':' + #reservationStatus")
    public List<GetCustomerReservationResponse> getCustomerReservation(Long customerId, String reservationStatus) {
        return reservationRepository.getAllByCustomer_Id(customerId, ReservationStatus.valueOf(reservationStatus));
    }
}
