package com.example.demo.customer;

import com.example.demo.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private JWTUtil jwtUtil;


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
    public ResponseEntity<?> addNewCustomer(@RequestBody Customer customer) {
        customerServiceImpl.addNewCustomer(customer);
        String token = jwtUtil.issueToken("ss@gmail.com", "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerServiceImpl.deleteCustomer(id);
    }

}
