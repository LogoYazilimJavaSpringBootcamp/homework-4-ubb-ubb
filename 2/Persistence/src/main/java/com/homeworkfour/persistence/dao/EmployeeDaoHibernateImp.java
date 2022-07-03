package com.homeworkfour.persistence.dao;

import com.homeworkfour.persistence.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate implementation of JPA.
 *
 */
public interface EmployeeDaoHibernateImp extends JpaRepository <Employee, Long>{

    Employee getEmployeeById(long id);
    void deleteById(long id);

}
