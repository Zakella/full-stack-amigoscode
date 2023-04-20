package com.zakella.customer;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDataAccessService implements CustomerDAO{
    public CustomerDataAccessService() {
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return List.of(
                new Customer(1, "Slava","slava@com", 36),
                new Customer(2, "Maria","maria@com", 28)
        );

    }
}
