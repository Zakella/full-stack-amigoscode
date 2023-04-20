package com.zakella.customer;

import com.zakella.exception.RecourseNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDataAccessService customerDataAccessService;

    public CustomerService(CustomerDataAccessService customerDataAccessService) {
        this.customerDataAccessService = customerDataAccessService;
    }

    public Customer getCustomer (Integer id){
        return customerDataAccessService.selectCustomerById(id)
                .orElseThrow(()-> new RecourseNotFound(
                        String.format("can find customer with id %s " , id)
                ));
    }

    public List<Customer> getAllCustomers(){
        return customerDataAccessService.selectAllCustomers();
    }

}
