package com.ylizma.bankmanagement.domain;

import com.ylizma.bankmanagement.model.Customer;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountInformation {

	private Long accountNumber;
	
	private BankInformation bankInformation;
	
	private String accountStatus;
	
	private String accountType;
	
	private Double accountBalance;
	
	private Date accountCreated;

	private Customer customer;
}
