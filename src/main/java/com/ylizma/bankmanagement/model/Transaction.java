package com.ylizma.bankmanagement.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TX_ID")
	private Long id;

	@ManyToOne
	private Account accountNumber;

	@Temporal(TemporalType.TIME)
	private Date txDateTime;

	//transaction Type
	@Enumerated(EnumType.STRING)
	private TransactionType txType;

	//transaction Amount
	private BigDecimal txAmount;
}
