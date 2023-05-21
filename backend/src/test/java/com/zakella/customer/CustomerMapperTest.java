package com.zakella.customer;

import com.zakella.mapStruct.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;


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
                .gender(Gender.MALE)
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
        assertThat(customerDTO.gender()).isEqualTo(Gender.MALE);

    }

}