package com.zakella.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;

    private final CustomerRoadMapper roadMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                         CustomerRoadMapper roadMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.roadMapper = roadMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = "SELECT id, name, email, age FROM customer;";
        return jdbcTemplate.query(sql , roadMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = "SELECT * FROM customer WHERE id = ?";
        return  jdbcTemplate.query(sql, roadMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = "" +
                "INSERT INTO customer (name, email, age) VALUES (?,?, ? )" +
                "";

        jdbcTemplate.update(sql, customer.getName(),
                customer.getEmail(),
                customer.getAge());

    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = "SELECT COUNT(*) FROM customer WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public boolean existsCustomerWithID(Integer id) {

        var sql = "SELECT COUNT(id) FROM customer WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;

    }

    @Override
    public void updateCustomer(Customer update) {

        var sql = "UPDATE customer SET name = ? , email = ? , age = ? WHERE  id = ?";
        jdbcTemplate.update(sql,
                update.getName(),
                update.getEmail(),
                update.getAge(),
                update.getId());


    }
}
