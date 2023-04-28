package com.zakella.customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository("faked")
public class CustomerListDataAccessService implements CustomerDAO{

    private List<Customer> customers = new ArrayList<>();

    public CustomerListDataAccessService() {
        customers.add(new Customer(1, "Slava","slava@com", 36));
        customers.add( new Customer(2, "Maria","maria@com", 28));;;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return  customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return selectAllCustomers()
                .stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public boolean existsCustomerWithID(Integer id) {
        return customers.stream().
                anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }


}
