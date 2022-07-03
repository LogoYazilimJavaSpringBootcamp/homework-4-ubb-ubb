package com.example.rabbitmqlistener.repository;

import com.example.rabbitmqlistener.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
