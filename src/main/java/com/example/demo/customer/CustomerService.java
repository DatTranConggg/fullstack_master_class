package com.example.demo.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // Creat a bean to inject multiple places => Same instance
// @Primary => default Bean
public class CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerService(@Qualifier("fake") CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> getCustomer() {
        return customerRepo.getCustomer();
    }
}
