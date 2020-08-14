package com.ylizma.bankmanagement.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountCustomerInfo {
    private Long accountNumber;

    private BankInformation bankInformation;

    private String accountStatus;

    private String accountType;

    private Double accountBalance;

    private Date accountCreated;

    private Long customerNumber;
}
