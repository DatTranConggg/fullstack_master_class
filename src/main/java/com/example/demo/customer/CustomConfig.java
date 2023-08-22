package com.example.demo.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("I am command line runner");
        };
    }
}
