package com.ylizma.bankmanagement.domain;

import com.ylizma.bankmanagement.model.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetails {

	private AccountInformation accountInformation;
	
	private Date txDateTime;
	
	private TransactionType txType;
	
	private BigDecimal txAmount;
}
