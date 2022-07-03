package com.logo.repository;
import com.logo.model.company.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * AccountRepository extends from CrudRepository
 * Uses JPA specification and Hibernate implementation and abstracts SQL queries *
 */
@Service
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByBankName(String bankName);
    List<Account> findByIban(String iban);
    Account findById(long id);
    Account deleteById(long id);
}
