package com.zakella.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataServiceTest {

    private AutoCloseable autoCloseable;

    CustomerJPADataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldSelectAllCustomers() {
        //given
        underTest.selectAllCustomers();
        //when
        verify(customerRepository).findAll();
        //then

    }

    @Test
    void itShouldSelectCustomerById() {
        //given
        int id = 1;
        //when
        underTest.selectCustomerById(id);
        //then
        verify(customerRepository).findById(id);

    }

    @Test
    void itShouldInsertCustomer() {
        //given
        Customer customer = new Customer("Joe", "joe@gmail.com", 20);
        underTest.insertCustomer(customer);
        //when
        //then

        verify(customerRepository).save(customer);

    }

    @Test
    void itShouldExistsCustomerWithEmail() {
        //given
        String email = "test@gmail.com";
        //when
        underTest.existsCustomerWithEmail(email);
        //then
        verify(customerRepository).existsCustomerByEmail(email);

    }

    @Test
    void itShouldDeleteCustomerById() {
        //given
        int id = 1;
        //when
        underTest.deleteCustomerById(id);
        //then
        verify(customerRepository).deleteById(id);


    }

    @Test
    void itShouldExistsCustomerWithID() {
        int id = 1;
        //when
        underTest.existsCustomerWithID(id);
        //then
        verify(customerRepository).existsCustomerById(id);

    }

    @Test
    void itShouldUpdateCustomer() {
        //given
        Customer customer = new Customer("Joe", "joe@gmail.com", 20);
        underTest.updateCustomer(customer);
        //when
        //then

        verify(customerRepository).save(customer);


    }
}