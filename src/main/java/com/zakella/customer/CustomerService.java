package com.zakella.customer;

import com.zakella.exception.DuplicateRecourseException;
import com.zakella.exception.RecourseNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDataAccessService;

    public CustomerService(@Qualifier("faked") CustomerDAO customerDataAccessService) {
        this.customerDataAccessService = customerDataAccessService;
    }

    public Customer getCustomer (Integer id){
        return customerDataAccessService.selectCustomerById(id)
                .orElseThrow(()-> new RecourseNotFoundException(
                        String.format("can find customer with id %s " , id)
                ));
    }

    public List<Customer> getAllCustomers(){
        return customerDataAccessService.selectAllCustomers();
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        if (customerDataAccessService.existsPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateRecourseException(String.format(
                    "Email %s exists!", customerRegistrationRequest.email()
            ));
        }

        customerDataAccessService.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()));

    }

    public void deleteCustomerById(Integer id) {
        if (customerDataAccessService.existsCustomerWithID(id)){
            customerDataAccessService.deleteCustomerById(id);


        }
        else throw new RecourseNotFoundException(
                String.format("can find customer with id %s " , id)) ;

    }
}
