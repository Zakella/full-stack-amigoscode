package com.zakella.customer;

import com.zakella.jwt.JWTUtil;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final JWTUtil jwtUtil;

    private final CustomerService customerService;

    public CustomerController(JWTUtil jwtUtil, CustomerService customerService) {
        this.jwtUtil = jwtUtil;
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerByI(@PathVariable(name = "id") Integer id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();

    }

    @DeleteMapping("/{id}")
    public void deleteCustomerByID(@PathVariable(name = "id") Integer id) {
        customerService.deleteCustomerById(id);

    }

    @PutMapping("{id}")
    public void updateCustomer(@PathVariable(name = "id") Integer id,
                               @RequestBody CustomerUpdateRequest customerDTO) {
        customerService.updateCustomer(id, customerDTO);

    }
}

