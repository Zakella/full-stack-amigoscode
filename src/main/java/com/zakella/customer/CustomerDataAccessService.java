package com.zakella.customer;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public class CustomerDataAccessService implements CustomerDAO{


    public CustomerDataAccessService() {
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return  List.of(
                new Customer(1, "Slava","slava@com", 36),
                new Customer(2, "Maria","maria@com", 28)
        );
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return selectAllCustomers()
                .stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }


}
