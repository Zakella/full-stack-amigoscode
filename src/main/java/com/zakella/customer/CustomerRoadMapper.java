package com.zakella.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRoadMapper implements RowMapper <Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setAge(rs.getInt("age"));
        return customer;
    }
}
