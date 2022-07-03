package com.homeworkfour.persistence.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import com.homeworkfour.persistence.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
/**
 * JDBCTemplate implementation of EmployeeDao class
 * Able to complete simple crud transactions.
 */
public class EmployeeDaoTemplateImp implements EmployeeDao<Employee> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Employee get(long id) {

        String sql = "SELECT * FROM employee WHERE ID = ?";
        return (Employee) jdbcTemplate.queryForObject(sql,
                                           new Object[]{id},
                                           new BeanPropertyRowMapper<>(Employee.class));

    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public Employee save(Employee employee) {
        jdbcTemplate.update("INSERT INTO employee(name,age,position) VALUES(?,?,?)", employee.getName(), employee.getAge(), employee.getPosition());

        return employee;
    }

    @Override
    public Employee update(Employee employee, long id) {

        String sql = "UPDATE employee SET name = ?, age = ?, position = ? WHERE id = ?";

        jdbcTemplate.update(sql,employee.getName(),employee.getAge(), employee.getPosition(), id);

        return employee;

    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM employee WHERE ID = ?";
        jdbcTemplate.update(sql,new Object[] {id});
    }
}
