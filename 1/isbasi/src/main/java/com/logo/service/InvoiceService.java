package com.logo.service;

import com.logo.model.company.Account;
import com.logo.model.invoice.Invoice;
import com.logo.repository.InvoiceRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Invoice service class contains simple business logic for Invoice objects.
 * Takes instruction from controller, submit them to Repository class, returns object to controller
 */
@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Invoice save(Invoice invoice) {
        rabbitTemplate.convertAndSend("isbasi-exchange","invoiceKey",invoice);
        return invoice;
    }

   public Invoice findById(long id) {
        return invoiceRepository.findById(id);
   }

   public void deleteById(long id) {
        invoiceRepository.deleteById(id);
   }

   public Invoice update(Invoice invoice) {

       Invoice invoiceToUpdate = invoiceRepository.findById(invoice.getId());

       invoiceToUpdate.setInvoiceDate(invoice.getInvoiceDate());
       invoiceToUpdate.setStatus(invoice.getStatus());
       invoiceToUpdate.setDiscount(invoice.getDiscount());

       return invoiceRepository.save(invoiceToUpdate);
   }
}
