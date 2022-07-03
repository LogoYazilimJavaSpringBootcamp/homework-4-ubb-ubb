package com.logo.service;

import com.logo.model.item.Product;
import com.logo.repository.AccountRepository;
import com.logo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class ProductService {


	@Autowired
	ProductRepository productRepository;

	public List<Product> getProductList() {
		return productRepository.getProductList();
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}


}
