package com.zakella.customer;

import org.checkerframework.checker.units.qual.C;
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

        //when
        Customer actual = underTest.mapRow(resultSet, 1);
        //then

        Customer expectedCustomer = new Customer(1, "Slava", "zakella@test.com", 20 );

        assertThat(expectedCustomer).isEqualTo(actual);


    }
}