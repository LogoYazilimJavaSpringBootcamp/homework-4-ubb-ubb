package com.logo.model.company;

import com.logo.model.Order;
import com.logo.model.enums.FirmType;
import com.logo.model.enums.Status;
import com.logo.model.invoice.Invoice;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
