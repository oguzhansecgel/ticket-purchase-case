package com.os.yerinial.controller;

import com.os.yerinial.model.dto.customer.response.GetCustomerReservationResponse;
import com.os.yerinial.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final String API_URL = "/api/v1/customer";


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/my-reservation/{customerId}")
    public List<GetCustomerReservationResponse> getCustomerAllReservation(@PathVariable("customerId") Long customerId) {
        logger.info(API_URL + "/my-reservation/{customerId} incoming request -> {}", customerId);
        return customerService.getCustomerReservation(customerId);
    }
}
