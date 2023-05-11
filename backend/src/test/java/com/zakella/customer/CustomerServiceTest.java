package com.zakella.customer;
import com.zakella.exception.DuplicateResourceException;
import com.zakella.exception.RequestValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import com.zakella.exception.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerDAO customerDao;

    CustomerService underTest;
    @Mock
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao, passwordEncoder);
    }



    @Test
    void canGetCustomer() {
        // Given
        int id = 10;



        Customer customer =  Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));


        // When

        Customer actual = customerDao.selectCustomerById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        String.format("customer with id [%s] not found" , id)
                ));

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        int id = 10;

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() ->
                underTest.getCustomer(id)).
                isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }


    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        // Given
        String email = "alex@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex", email, 19, Gender.Male, "password");

        // When
        assertThatThrownBy(() -> underTest.addCustomer(request)).isInstanceOf(DuplicateResourceException.class).hasMessage("email already taken");

        // Then
        verify(customerDao, never()).insertCustomer(any());
    }


    @Test
    void itShouldGetAllCustomers() {
        //given
        underTest.getAllCustomers();
        //when
        //then

        verify(customerDao).selectAllCustomers();

    }

    @Test
    void addCustomer() {

        String email = "alex@gmail.com";

        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);


        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex", email, 19, Gender.Male, "password");

        String passHash = "aaa111222243444";
        when(passwordEncoder.encode("password")).thenReturn(passHash);

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getGender()).isEqualTo(request.gender());
        assertThat(capturedCustomer.getPassword()).isEqualTo(passHash);

    }

    @Test
    void deleteCustomerById() {
        // Given
        int id = 10;

        when(customerDao.existsCustomerWithID(id)).thenReturn(true);

        // When
        underTest.deleteCustomerById(id);
        // Then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        // Given
        int id = 10;

        when(customerDao.existsCustomerWithID(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id)).isInstanceOf(ResourceNotFoundException.class).hasMessage("customer with id [%s] not found".formatted(id));

        // Then
        verify(customerDao, never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomersProperties() {
        // Given
        int id = 10;
        Customer customer = Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", newEmail, 23);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        int id = 10;
        Customer customer =  Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", null, null);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        int id = 10;
        Customer customer =  Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // Given
        int id = 10;
        Customer customer =  Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, 22);

        // When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        int id = 10;
        Customer customer =  Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest)).isInstanceOf(DuplicateResourceException.class).hasMessage("email already taken");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        int id = 10;
        Customer customer =  Customer.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .age(19)
                .gender(Gender.Male)
                .password("password")
                .build();;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(), customer.getEmail(), customer.getAge());

        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest)).isInstanceOf(RequestValidationException.class).hasMessage("no data changes found");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
}