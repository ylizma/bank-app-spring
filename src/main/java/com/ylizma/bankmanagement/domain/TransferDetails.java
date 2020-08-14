package com.ylizma.bankmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferDetails {

	private Long fromAccountNumber;
	
	private Long toAccountNumber;
	
	private BigDecimal transferAmount;
}
