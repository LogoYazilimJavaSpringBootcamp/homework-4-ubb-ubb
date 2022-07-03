package com.example.rabbitmqlistener.model;


import com.example.rabbitmqlistener.enums.SalesType;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Satış faturaları => Müşteri ...
// Alış faturaları => Tedarikçi...
@Data
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                                  // auto calculated
    private String invoiceDate;                      // required
    private String dueDate;                          // required
    private String status;                             // required //AKTIF PASIF CANCEL
    private SalesType salesType;                        // required //RETAIL OR WHOLESALE
    private double vat;                                 // required
    @Transient
    private Customer customer;                          // required
    @Transient
    private Account account;                            // required

    @Transient
    private List<Product> productList;                  // optional
    private double discount;                            // optional  else 0;

    private static List<Invoice> invoiceList = new ArrayList<>();  //can be deleted

    private double vatTotal;                            // not required auto calculated
    private double totalPriceWithVat;                   // not required auto calculated
    private double totalPrice;                          // not required auto calculated



    @Bean
    private void calculateTotalPrice() {
        totalPrice = productList.stream().mapToDouble(o -> o.getPrice()).sum();
        // fdsdf
        totalPrice = totalPrice - (totalPrice * discount);

        if (salesType == SalesType.RETAIL) {
            vatTotal = totalPrice * vat;
        } else if (salesType == SalesType.WHOLESALE) {
            vatTotal = totalPrice;
        }
        totalPriceWithVat = totalPrice + vatTotal;
    }

}
