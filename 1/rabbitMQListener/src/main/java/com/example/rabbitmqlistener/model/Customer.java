package com.example.rabbitmqlistener.model;


import com.example.rabbitmqlistener.enums.FirmType;
import com.example.rabbitmqlistener.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer {

	protected Customer(){}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;

	@Column
	private FirmType firmType;
	@Column
	private Status status;

	@OneToMany
	@JoinColumn(name = "account_id", insertable = true,updatable = false)
	private List<Account> accountList;






}
