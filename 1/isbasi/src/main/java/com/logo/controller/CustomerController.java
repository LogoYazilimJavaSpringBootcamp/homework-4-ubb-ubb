package com.logo.controller;

import com.logo.model.company.Customer;
import com.logo.model.enums.Status;
import com.logo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class represents controller for Customer.
 * Client will be communicated with this class.
 * Requests are transferred to service class.
 */
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping(value = "/all")
    public Iterable<Customer> findAll() {
        return customerService.findAllCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer findById(@PathVariable long id) {
        return customerService.findById(id);
    }

    @GetMapping
    public Iterable<Customer> findByStatusAndName(@RequestParam(value = "status") Optional<Status> status, @RequestParam(value = "name") Optional<String> name) {
    return customerService.findByStatusAndName(status,name);
    }

    @DeleteMapping
    public Customer deleteById(@RequestParam(value ="id") long id) {
        return customerService.deleteById(id);
    }
}
