package com.zakella.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    CustomerDataAccessService customerDataAccessService;

    public CustomerService(CustomerDataAccessService customerDataAccessService) {
        this.customerDataAccessService = customerDataAccessService;
    }

    public Customer getCustomer (Integer id){
        return customerDataAccessService.selectAllCustomers()
                .stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(
                        String.format("can find customer with id %s " , id)
                ));
    }

    public List<Customer> getAllCustomers(){
        return customerDataAccessService.selectAllCustomers();
    }

}
