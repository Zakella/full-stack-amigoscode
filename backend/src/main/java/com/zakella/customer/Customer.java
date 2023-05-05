package com.zakella.customer;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "customer")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String name;
    private  String email;
    private  Integer age;

    @Enumerated(EnumType.STRING)
    private  Gender gender;

}