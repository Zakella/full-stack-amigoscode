package com.zakella.customer;

import com.zakella.exception.DuplicateResourceException;
import com.zakella.exception.ResourceNotFoundException;
import com.zakella.exception.RequestValidationException;
import com.zakella.mapStruct.CustomerMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDAO customerDao;
    private final PasswordEncoder passwordEncoder;


//    private final CustomerMapper customerMapper;

    public CustomerService(@Qualifier("jpa")
                           CustomerDAO customerDataAccessService,
                           PasswordEncoder passwordEncoder) {
        this.customerDao = customerDataAccessService;
        this.passwordEncoder = passwordEncoder;
        ;

    }

    public CustomerDTO getCustomer (Integer id){

        return    CustomerMapper.INSTANCE.customerToCustomerDTO(
                customerDao.selectCustomerById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "customer with id [%s] not found".formatted(id)))
        );
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerDao.selectAllCustomers()
                .stream()
                .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
                .collect(Collectors.toList());
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
                .password(
                        passwordEncoder.encode(customerRegistrationRequest.password())
                )
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

        Customer customer = customerDao.selectCustomerById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("customer with id [%s] not found" , id)
                ));




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
