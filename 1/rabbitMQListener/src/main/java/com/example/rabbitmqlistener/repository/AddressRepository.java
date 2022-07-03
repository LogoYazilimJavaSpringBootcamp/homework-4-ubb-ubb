package com.example.rabbitmqlistener.repository;

import com.example.rabbitmqlistener.model.Account;
import com.example.rabbitmqlistener.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AddressRepository extends JpaRepository<Address, Long> {

}
