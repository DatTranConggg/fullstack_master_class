package com.example.demo.customer;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("fake")
public class CustomerFakeRepo implements CustomerRepo{
    @Override
    public List<Customer> getCustomer() {
        return Arrays.asList(new Customer(1, "Cong Dat 1"), new Customer(2, "Cong Dat 2"));
    }
}
