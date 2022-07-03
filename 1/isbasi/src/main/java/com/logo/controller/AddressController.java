package com.logo.controller;

import com.logo.model.company.Address;
import com.logo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents controller for Address.
 * Client will be communicated with this class.
 * Requests are transferred to service class.
 */
@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public Address save(@RequestBody Address address) {
        return addressService.save(address);
    }

    @GetMapping(value = "/{id}")
    public Address findById(@PathVariable long id) {
        return addressService.findById(id);
    }

    @DeleteMapping
    public void deleteById(@RequestParam(value = "id") long id) {
        addressService.deleteById(id);
    }

    @PutMapping
    public Address update(@RequestBody Address address) {
        return addressService.update(address);
    }
}
