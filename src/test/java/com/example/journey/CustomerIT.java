package com.example.journey;


import com.example.demo.DemoApplication;
import com.example.demo.customer.Customer;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootTest(classes = DemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIT {

    @Autowired
    private WebTestClient webTestClient;

    private static final String CUSTOMER_URI = "/api/v1/customers";

    @Test
    void canRegisterACustomer() {
        // Create a customer request
        Faker faker = new Faker();
        Name name = faker.name();
        String email = name.lastName() + UUID.randomUUID() + "@gmail.com";
        int age = RandomUtils.nextInt(0, 100);

        Customer customer = new Customer(name.toString(), email, age);

        // Send the request to the server
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer), Customer.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customers

    }
}
