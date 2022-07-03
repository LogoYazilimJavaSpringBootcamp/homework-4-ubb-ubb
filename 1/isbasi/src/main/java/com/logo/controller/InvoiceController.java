package com.logo.controller;

import com.logo.model.invoice.Invoice;
import com.logo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents controller for Invoice.
 * Client will be communicated with this class.
 * Requests are transferred to service class.
 */
@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping
    public Invoice save(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice);
    }

    @GetMapping(value = "/{id}")
    public Invoice findById(@PathVariable long id) {
        return invoiceService.findById(id);
    }

    @DeleteMapping
    public void deleteById(@RequestParam(value = "id") long id) {
        invoiceService.deleteById(id);
    }

    @PutMapping
    public Invoice update(@RequestBody Invoice invoice) {
        return invoiceService.update(invoice);
    }
}
