package com.logo.controller;

import com.logo.model.company.Account;
import com.logo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents controller for Accounts.
 * Client will be communicated with this class.
 * Requests are transferred to service class.
 */
@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
        return account;
    }

    @GetMapping(value = "/{id}")
    public Account findById(@PathVariable long id) {
        return accountService.findById(id);
    }

    @GetMapping(value = "/all")
    public Iterable<Account> findAll() {
        return accountService.findAll();
    }

    @PutMapping(value = "/{id}")
    public Account changeAccount(@PathVariable long id, @RequestBody Account account) {
        return accountService.updateAccount(id,account);
    }

    @DeleteMapping
    public Account deleteAccount(@RequestParam(value = "id") long id) {
        return accountService.deleteAccount(id);
    }
}
