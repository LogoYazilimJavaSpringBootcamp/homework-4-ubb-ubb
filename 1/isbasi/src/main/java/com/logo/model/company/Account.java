package com.logo.model.company;

import com.logo.model.User;
import com.logo.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *  Account class represents accounts of companies.
 */
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String bankName;
    @Column
    private String iban;
    @Column
    private Currency currency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id" , insertable = true, updatable = false)
    private Customer customer;

    @Column
    private Double balance;

    protected Account()  {}


    public Account(String bankName, String iban, Currency currency,Customer customer, Double balance) {
        this.bankName = bankName;
        this.iban = iban;
        this.currency = currency;
        this.balance = balance;
        //this.customer = customer;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', IBAN='%s']",
                id, bankName, iban);
    }
}
