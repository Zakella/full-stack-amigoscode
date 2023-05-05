package com.zakella.customer;
import com.zakella.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private final CustomerRoadMapper customerRoadMapper
            = new CustomerRoadMapper();

    private CustomerJDBCDataAccessService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRoadMapper);
    }

    @Test
    void itShouldSelectAllCustomers() {



        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();

        underTest.insertCustomer(customer);

        // When
        List<Customer> actual = underTest.selectAllCustomers();

        // Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void itShouldSelectCustomerById() {
        //given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();;
        underTest.insertCustomer(customer);

        //when

       Integer id = underTest.selectAllCustomers()
               .stream()
               .filter(c -> c.getEmail().equals(email))
               .map(Customer::getId)
               .findFirst()
               .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        //then

        assertThat(actual).isPresent().hasValueSatisfying(
                c ->{
                assertThat(c.getId()).isEqualTo(id);
                assertThat(c.getName()).isEqualTo(customer.getName());
                assertThat(c.getAge()).isEqualTo(customer.getAge());
                assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                }

        );

    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        // Given
        int id = 0;

        // When
        var actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isEmpty();
    }


    @Test
    void itShouldInsertCustomer() {
        //given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();;
        underTest.insertCustomer(customer);
        //when

        boolean actual = underTest.existsCustomerWithEmail(email);

        //then

        assertThat(actual).isTrue();

    }

    @Test
    void itShouldExistsCustomerWithEmail() {
        //given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();;
        underTest.insertCustomer(customer);

        //when

        String actual = underTest.selectAllCustomers()
                .stream()
                .map(Customer::getEmail)
                .filter(cEmail -> cEmail.equals(email))
                .findFirst()
                .orElseThrow();
        //then

        assertThat(email).isEqualTo(actual);

    }

    @Test
    void itShouldDeleteCustomerById() {
        //given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();;
        underTest.insertCustomer(customer);


        //when

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.deleteCustomerById(id);

        //then
        assertThat(underTest.existsCustomerWithID(id)).isFalse();

    }

    @Test
    void itShouldExistsCustomerWithID() {
        //given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();;
        underTest.insertCustomer(customer);

        //when

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        //then

        assertThat(actual).isPresent();

    }

    @Test
    void itShouldUpdateCustomer() {
        //given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .gender(Gender.Male)
                .build();;
        underTest.insertCustomer(customer);


        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent();

        Customer customerEdit = actual.get();
        customerEdit.setName("Slava");

        //when
        underTest.updateCustomer(customerEdit);
        Optional<Customer> editCustomer = underTest.selectCustomerById(id);

        //then
        assertThat(editCustomer).isPresent();
        assertThat(editCustomer.get().getId()).isEqualTo(id);
        assertThat(editCustomer.get().getName()).isEqualTo("Slava");


    }
}