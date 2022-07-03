package com.logo.repository;
import com.logo.model.company.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * AddressRepository extends from CrudRepository
 * Uses JPA specification and Hibernate implementation and abstracts SQL queries *
 */
@Service
public interface AddressRepository extends CrudRepository<Address,Long> {
    public Address findByProvince(String province);
    public Address findByCountry(String country);
    public Address findById(long id);
    public Address deleteById(long id);
}
