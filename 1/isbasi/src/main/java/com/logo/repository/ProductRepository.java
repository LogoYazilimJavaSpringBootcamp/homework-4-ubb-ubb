package com.logo.repository;

import com.logo.model.company.Account;
import com.logo.model.item.Product;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    public static List<Product> productList = new ArrayList<>();

    public Product save(Product product) {
        productList.add(product);
        return product;
    }

    public List<Product> getProductList() {
        return productList;
    }


}
