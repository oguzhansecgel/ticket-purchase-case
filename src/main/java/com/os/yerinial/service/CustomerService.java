package com.os.yerinial.service;

import com.os.yerinial.model.dto.customer.response.GetCustomerReservationResponse;
import com.os.yerinial.model.entity.Reservation;
import com.os.yerinial.model.repository.CustomerRepository;
import com.os.yerinial.model.repository.EventRepository;
import com.os.yerinial.model.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<GetCustomerReservationResponse> getCustomerReservation(Long customerId) {
        return reservationRepository.getAllByCustomer_Id(customerId);
    }
}
