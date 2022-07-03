package com.logo.model.company;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table (name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String country;
	@Column
	private String province;
	@Column
	private String address;

	protected Address() {}

	public Address(String country, String province, String address) {
		super();
		this.country = country;
		this.province = province;
		this.address = address;
	}



}
