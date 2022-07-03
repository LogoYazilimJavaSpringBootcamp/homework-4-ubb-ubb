package com.homeworkfour.persistence.service;

import com.homeworkfour.persistence.dao.EmployeeDaoHibernateImp;
import com.homeworkfour.persistence.dao.EmployeeDaoJDBCImp;
import com.homeworkfour.persistence.dao.EmployeeDaoTemplateImp;
import com.homeworkfour.persistence.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * All three approaches are given in this class. Implementations are different than each other.
 * EmployeeService is used in CommandLineRunner in main application.
 * All three services conduct simple CRUD operations, create, get, update and delete.
 */
public class EmployeeService {

    // JDBC Core implementation
    @Autowired
    EmployeeDaoJDBCImp employeeDaoJDBCImp;

    // JDBCTemplate Implementation
    @Autowired
    EmployeeDaoTemplateImp employeeDaoTemplateImp;

    // Hibernate Implementation
    @Autowired
    EmployeeDaoHibernateImp employeeDaoHibernateImp;


    // JDBC:
    public Employee jdbcCreate(Employee employee) {
        return employeeDaoJDBCImp.save(employee);
    }

    public Employee jdbcGetById(long id) {
        return employeeDaoJDBCImp.get(id);
    }

    public void jdbcDeleteById(long id) {
        employeeDaoJDBCImp.delete(id);
    }

    public Employee jdbcUpdateById(Employee employee, long id) {
        return employeeDaoJDBCImp.update(employee, id);
    }


    // JDBC Template
    public void jdbcTemplateCreate(Employee employee) {
        employeeDaoTemplateImp.save(employee);
    }

    public Employee jdbcTemplateUpdate(Employee employee, long id) {
        return employeeDaoTemplateImp.update(employee, id);
    }

    public void jdbcTemplateDelete(long id) {
        employeeDaoTemplateImp.delete(id);
    }

    public Employee jdbcTemplateGetById(long id) {
        return employeeDaoTemplateImp.get(id);
    }


    // Hibernate;
    public void hibernateDelete(long id) {
        employeeDaoHibernateImp.deleteById(id);
    }

    public Employee hibernateGetById(long id) {
        return employeeDaoHibernateImp.getEmployeeById(id);
    }

    public Employee hibernateUpdate(Employee employee, long id) {
        Employee employeeToUpdate = employeeDaoHibernateImp.findById(id).get();
        employeeToUpdate.setAge(employee.getAge());
        employeeToUpdate.setName(employee.getName());
        employeeToUpdate.setPosition(employee.getPosition());
        return employeeDaoHibernateImp.save(employeeToUpdate);
    }

    public Employee hibernateCreate(Employee employee) {
        return employeeDaoHibernateImp.save(employee);
    }
}
