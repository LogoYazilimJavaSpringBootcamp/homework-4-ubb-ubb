package com.logo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import com.logo.model.enums.Status;
import com.logo.repository.CustomerRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.logo.model.company.Customer;
import com.logo.model.Order;
import com.logo.model.item.Product;
import org.springframework.transaction.annotation.Transactional;

/**
 * Customer service class contains simple business logic for Customer objects.
 * Takes instruction from controller, submit them to Repository class, returns object to controller
 */
@Service
@Transactional
public class CustomerService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public Customer createCustomer(Customer customer) {
		rabbitTemplate.convertAndSend("isbasi-exchange","customerKey",customer);
		return customer;
	}

	public Iterable<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer findById(long id) {
		return customerRepository.findById(id);
	}

	public Iterable<Customer> findByStatusAndName(Optional<Status> status, Optional<String> name) {

		if (status.isEmpty() || name.isEmpty()) {
			return customerRepository.findByStatusOrName(status,name);
		}

		return customerRepository.findByStatusAndName(status, name);
	}

	public Iterable<Customer> findByName(String name) {
		return customerRepository.findByName(name);
	}

	public Iterable<Customer> findByStatus(Status status) {
		return customerRepository.findByStatus(status);
	}

	public Customer deleteById(long id) {
		return customerRepository.deleteById(id);
	}
}
