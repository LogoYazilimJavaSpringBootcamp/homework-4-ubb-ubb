package com.example.rabbitmqlistener.model;


import com.example.rabbitmqlistener.enums.ProductType;
import com.example.rabbitmqlistener.enums.Unit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {


	private String name;
	private Unit unit;
	private Double quantity;
	private Double price;
	private ProductType productType;
	private Double vat;

	private Double vatPrice;
	private Double totalPrice;



	public Product(String name, Unit unit, Double quantity, Double price, ProductType productType, Double vat){
		this.name = name;
		this.unit = unit;
		this.quantity = quantity;
		this.price = price;
		this.productType = productType;
		this.vat = vat;
		calculateTotalPrice();
	}

	private void calculateTotalPrice() {
		vatPrice = (quantity * price) * vat;
		totalPrice = (quantity * price) + vatPrice;
	}

}
