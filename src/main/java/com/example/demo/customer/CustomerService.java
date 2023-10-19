package com.example.demo.customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(Integer id);

    void addNewCustomer(Customer customer);

    void deleteCustomer(Integer id);

    void updateCustomer(Integer id, String name, String email, Integer age);
}
