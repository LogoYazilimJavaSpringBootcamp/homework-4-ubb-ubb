package com.logo.repository;
import com.logo.model.invoice.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * InvoiceRepository extends from CrudRepository
 * Uses JPA specification and Hibernate implementation and abstracts SQL queries *
 */
@Service
public interface InvoiceRepository extends CrudRepository<Invoice,Long> {
    public Invoice findById(long id);
    public Invoice findByTotalPrice(double price);
    public Invoice deleteById(long id);
}
