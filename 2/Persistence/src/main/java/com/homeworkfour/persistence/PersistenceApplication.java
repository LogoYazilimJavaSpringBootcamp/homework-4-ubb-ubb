package com.homeworkfour.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeworkfour.persistence.model.Employee;
import com.homeworkfour.persistence.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
/**
 * This client is prepared for analysing three different persistence approach, JDBC core, JDBCTemplate and Hibernate.
 */
public class PersistenceApplication implements CommandLineRunner {


    @Autowired
    EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(PersistenceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Sample employee data
        Employee employee = new Employee(1L, "Umut", 16, "JDBC Template User");
        Employee employee2 = new Employee(1L, "Gulfem", 17, "Hibernate User");
        Employee employee3 = new Employee(2L, "Batu", 18, "JDBC User");


        // Jdbc Connection
        employeeService.jdbcDeleteById(2);
        employeeService.jdbcCreate(employee3);
        employeeService.jdbcGetById(20);
        employeeService.jdbcUpdateById(employee2, 10);


        // Using JDBC Template
        employeeService.jdbcTemplateCreate(employee);
        employeeService.jdbcTemplateDelete(6);
        log.info("JDBC Template Get By Id" + employeeService.jdbcTemplateGetById(4));
        employeeService.jdbcTemplateUpdate(employee2, 7);

        // Using Hibernate
        employeeService.hibernateCreate(employee3);
        employeeService.hibernateDelete(1);
        employeeService.hibernateGetById(3);
        employeeService.hibernateUpdate(employee3, 5);
    }
}
