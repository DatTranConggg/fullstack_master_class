package com.example.demo.customer;

import org.springframework.stereotype.Component;

import java.util.List;

@Component // Creat a bean to inject multiple places => Same instance
// @Primary => default Bean
public class CustomerServiceImpl implements CustomerService {

        private final CustomerRepository customerRepository;

        public CustomerServiceImpl(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Customer with id " + id + " does not exist"));
    }

    @Override
    public void addNewCustomer(Customer customer) {
            customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Customer with id " + id + " does not exist"));
            customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Integer id, String name, String email, Integer age) {
            Customer customer = customerRepository.findById(id).orElseThrow(
                    () -> new IllegalStateException("Customer with id " + id + " does not exist"));
            if (name != null && name.length() > 0 && !name.equals(customer.getName())) {
                customer.setName(name);
            }
            if (email != null && email.length() > 0 && !email.equals(customer.getEmail())) {
                customer.setEmail(email);
            }
            if (age != null && age > 0 && !age.equals(customer.getAge())) {
                customer.setAge(age);
            }
            customerRepository.save(customer);
    }
}
