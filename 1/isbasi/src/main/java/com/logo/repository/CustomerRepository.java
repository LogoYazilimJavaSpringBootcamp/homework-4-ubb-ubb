package com.logo.repository;
import com.logo.model.company.Customer;
import com.logo.model.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * CustomerRepository extends from CrudRepository
 * Uses JPA specification and Hibernate implementation and abstracts SQL queries *
 */
@Service
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    public Customer findById(long id);
    public List<Customer> findByName(String name);
    public List<Customer> findByStatus(Status status);
    public Customer deleteById(long id);
    public List<Customer> findByStatusAndName(Optional<Status> status, Optional<String> name);
    public List<Customer> findByStatusOrName(Optional<Status> status, Optional<String> name);
}
