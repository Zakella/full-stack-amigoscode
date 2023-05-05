package com.zakella.customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Entity
@Table( name = "customer"
, uniqueConstraints = {
        @UniqueConstraint(name = "customer_email_unique",
                columnNames = "email")

})


@Data
@NoArgsConstructor

public class Customer {

    @Id
    private Integer id;
    private String name;
    private String email;
    private String age;
    private Gender gender;


}
