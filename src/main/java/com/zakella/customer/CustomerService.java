package com.zakella.customer;

import com.zakella.exception.DuplicateResourceException;
import com.zakella.exception.RecourseNotFoundException;
import com.zakella.exception.RequestValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDao;


//    private final CustomerMapper customerMapper;

    public CustomerService(@Qualifier("jpa")
                           CustomerDAO customerDataAccessService
                         ) {
        this.customerDao = customerDataAccessService;;

    }

    public Customer getCustomer (Integer id){
        return customerDao.selectCustomerById(id)
                .orElseThrow(()-> new RecourseNotFoundException(
                        String.format("can find customer with id %s " , id)
                ));
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        if (customerDao.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException(String.format(
                    "Email %s exists!", customerRegistrationRequest.email()
            ));
        }

        customerDao.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()));

    }

    public void deleteCustomerById(Integer id) {
        if (customerDao.existsCustomerWithID(id)){
            customerDao.deleteCustomerById(id);


        }
        else throw new RecourseNotFoundException(
                String.format("can find customer with id %s " , id)) ;

    }

    public void updateCustomer(Integer id, UpdateRequest updateRequest) {

        Customer customer = getCustomer(id);

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);

    }
}
