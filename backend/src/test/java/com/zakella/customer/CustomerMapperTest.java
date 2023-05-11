package com.zakella.customer;

import com.zakella.customer.Customer;
import com.zakella.customer.CustomerDTO;
import com.zakella.customer.Gender;
import com.zakella.mapStruct.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class CustomerMapperTest {

    @Mock
    private Customer customer;

    @InjectMocks
    private CustomerMapper mapper;

    @Test
    void itShouldCustomerToCustomerDTO() {
        Customer customer = Customer.builder()
                .id(1)
                .name("Slava")
                .email("z@gmail.com")
                .age(36)
                .gender(Gender.Male)
                .build();
        //Given

        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO( customer );
        //When
        //Then

        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.name()).isEqualTo("Slava");
        assertThat(customerDTO.id()).isEqualTo(1);

        assertThat(customerDTO.email()).isEqualTo("z@gmail.com");
        assertThat(customerDTO.age()).isEqualTo(36);
        assertThat(customerDTO.gender()).isEqualTo(Gender.Male);

    }

}