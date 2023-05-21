package com.zakella.auth;

import com.zakella.customer.Customer;
import com.zakella.customer.CustomerDTO;
import com.zakella.mapStruct.CustomerMapper;
import com.zakella.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        Customer principal = (Customer) authentication.getPrincipal();
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(principal);
        String token = jwtUtil.issueToken(customerDTO.email(), customerDTO.roles());
        return new AuthenticationResponse(token, customerDTO);
    }

}