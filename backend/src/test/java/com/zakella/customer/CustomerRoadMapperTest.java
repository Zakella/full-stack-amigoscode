package com.zakella.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerRoadMapperTest {
    CustomerRoadMapper underTest;

    @Mock
    ResultSet resultSet;


    @BeforeEach
    void setUp() {
        underTest = new CustomerRoadMapper();
    }

    @Test
    void itShouldMapRow() throws SQLException {
        //given

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Slava");
        when(resultSet.getString("email")).thenReturn("zakella@test.com");
        when(resultSet.getInt("age")).thenReturn(20);
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("gender")).thenReturn(Gender.MALE.name());
//        when(resultSet.ge("age")).thenReturn(20);

        //when
        Customer actual = underTest.mapRow(resultSet, 1);
        //then

        Customer expectedCustomer = Customer.builder()
                .id(1)
                .name("Slava")
                .email("zakella@test.com")
                .age(20)
                .gender(Gender.MALE)
                .password("password")
                .build();

        assertThat(expectedCustomer).isEqualTo(actual);


    }
}