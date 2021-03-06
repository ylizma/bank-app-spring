package com.ylizma.bankmanagement.domain;
import lombok.*;

import java.math.BigDecimal;
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
	
	private BigDecimal accountBalance;
	
	private Date accountCreated;

	private CustomerDetails customerDetails;
}
