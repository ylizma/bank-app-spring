package com.ylizma.bankmanagement.service;

import java.util.List;

import com.ylizma.bankmanagement.domain.*;
import org.springframework.http.ResponseEntity;

public interface BankingService {

    public List<CustomerDetails> findAllCustomers();

    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);

    public CustomerDetails findByCustomerNumber(Long customerNumber);

    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);

    public ResponseEntity<Object> deleteCustomer(Long customerNumber);

    public ResponseEntity<Object> findByAccountNumber(Long accountNumber);

    public ResponseEntity<Object> addNewAccount(AccountCustomerInfo accountInformation);

    public List<AccountInformation> findAllAccounts();

    public AccountInformation findAccountByCustomerNumber(Long customerNumber);

    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber);

    public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber);

}
