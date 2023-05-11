package com.zakella.mapStruct;

import com.zakella.customer.Customer;
import com.zakella.customer.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );


    @Mapping(target = "roles", expression = "java(mapAuthoritiesToRoles(customer.getAuthorities()))")
    CustomerDTO customerToCustomerDTO(Customer customer);

    default List<String> mapAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

}