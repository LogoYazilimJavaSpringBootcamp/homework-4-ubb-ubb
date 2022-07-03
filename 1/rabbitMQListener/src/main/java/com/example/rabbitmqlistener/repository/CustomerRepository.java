package com.example.rabbitmqlistener.repository;

import com.example.rabbitmqlistener.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
