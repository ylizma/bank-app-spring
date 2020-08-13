package com.ylizma.bankmanagement.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ADDR_ID")
	private Long id;

	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;

//	@OneToMany(mappedBy = "customerAddress")
//	private List<Customer> customer;

}
