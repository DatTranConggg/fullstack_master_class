package com.example.demo;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			Customer customer_01 = new Customer("James Bond 1", "aa", 20);
			Customer customer_02 = new Customer( "James Bond 2 ", "aa", 20);
			Customer customer_03 = new Customer( "James Bond 3 ", "a", 30);
			Customer customer_04 = new Customer( "James Bond 4 ", "aaa", 40);

			List<Customer> customers = List.of(customer_01, customer_02, customer_03, customer_04);
			customerRepository.saveAll(customers);
		};
	}

}
