package com.example.demo;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import com.example.demo.customer.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {

    private CustomerServiceImpl underTest;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerServiceImpl(customerRepository, passwordEncoder);
    }

    @Test
    void testGetCustomers() {
        // When
        underTest.getCustomers();

        // Then
        verify(customerRepository).findAll();
    }

    @Test
    void testFindCustomerById() {
        // Given
        Integer id = 1;
        Customer customer = new Customer(id, "James", "google@gmail.com", 20);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.getCustomerById(1);

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenFindCustomerReturnEmpty () {
        // Given
        Integer id = 11;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Customer with id " + id + " does not exist");
    }

    @Test
    void testAddCustomer() {
        // Given
        String email = "fakeEmail";
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(false);

        Customer request = new Customer("James", email, 20);

        // When
        underTest.addNewCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.getAge());
    }

    @Test
    void willThrowWhenEmailExistWhileAddingCustomer() {
        // Given
        String email = "fakeEmail";
        when(customerRepository.existsCustomerByEmail(email)).thenReturn(true);

        // When
        // Then
        assertThatThrownBy(() -> underTest.addNewCustomer(new Customer("James", email, 20)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testDeleteCustomerById() {
//        // Given
//        Integer id = 1;
//        when(customerRepository.existsById(id)).thenReturn(true);
//
//        // When
//        underTest.deleteCustomer(id);
//
//        // Then
//        verify(customerRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeletingCustomerById() {
//        // Given
//        Integer id = 2;
//        when(customerRepository.existsById(id)).thenReturn(false);
//
//        // When
//        assertThatThrownBy(() -> underTest.deleteCustomer(id))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("Customer with id " + id + " does not exist");
//        // Then
//        verify(customerRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateCustomerById() {

    }
}
