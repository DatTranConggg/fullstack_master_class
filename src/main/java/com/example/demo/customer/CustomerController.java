package com.example.demo.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;


    @GetMapping
    public List<Customer> getCustomers() {
        return customerServiceImpl.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Integer id) {
        return customerServiceImpl.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Integer id,@RequestBody Customer customer) {
        customerServiceImpl.updateCustomer(id, customer.getName(), customer.getEmail(), customer.getAge());
        return customerServiceImpl.getCustomerById(id);
    }

    @PostMapping
    public void addNewCustomer(@RequestBody Customer customer) {
        customerServiceImpl.addNewCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerServiceImpl.deleteCustomer(id);
    }

}
