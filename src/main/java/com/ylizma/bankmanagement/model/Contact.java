package com.ylizma.bankmanagement.model;

import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONTACT_ID")
	private Long id;

	private String emailId;

	private String homePhone;

	private String workPhone;

//	@OneToOne(mappedBy = "contactDetails")
//	private Customer customer;


}
