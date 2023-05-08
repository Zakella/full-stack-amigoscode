package com.zakella.customer;

import com.zakella.exception.DuplicateResourceException;
import com.zakella.exception.ResourceNotFoundException;
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
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("customer with id [%s] not found" , id)
                ));
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        if (customerDao.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException(String.format(
                    "email already taken", customerRegistrationRequest.email()
            ));
        }

        Customer customer = Customer.builder()
                .name(customerRegistrationRequest.name())
                .email(customerRegistrationRequest.email())
                .age(customerRegistrationRequest.age())
                .gender(customerRegistrationRequest.gender())
                .build();

        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id) {
        if (customerDao.existsCustomerWithID(id)){
            customerDao.deleteCustomerById(id);


        }
        else throw new ResourceNotFoundException(
                String.format("customer with id [%s] not found" , id)) ;

    }

    public void updateCustomer(Integer id, CustomerUpdateRequest updateRequest) {

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
