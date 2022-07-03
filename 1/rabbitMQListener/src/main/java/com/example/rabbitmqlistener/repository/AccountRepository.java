package com.example.rabbitmqlistener.repository;

import com.example.rabbitmqlistener.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
