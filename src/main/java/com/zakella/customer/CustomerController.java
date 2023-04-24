package com.zakella.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerByI(@PathVariable(name = "id") Integer id){
        return customerService.getCustomer(id);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest CustomerRegistrationRequest){
        customerService.addCustomer(CustomerRegistrationRequest);


    }

    @DeleteMapping("/{id}")
    public void deleteCustomerByID(@PathVariable(name = "id") Integer id){
        customerService.deleteCustomerById(id);

    }

    @PutMapping("{id}")
   public void updateCustomer(@PathVariable(name = "id") Integer id,
                                  @RequestBody CustomerUpdateRequest customerDTO){
      customerService.updateCustomer(id, customerDTO);

   }
}

