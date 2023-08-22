package com.example.demo.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    // private final CustomerService customerService = new CustomerService(); // Bad code

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService, CustomerService customerService1) {
        this.customerService = customerService1;
    }

    @GetMapping
    List<Customer> getCustomers() {
        return customerService.getCustomer();
    }
}
