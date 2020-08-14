package com.ylizma.bankmanagement.controller;

import com.ylizma.bankmanagement.domain.AccountCustomerInfo;
import com.ylizma.bankmanagement.domain.AccountInformation;
import com.ylizma.bankmanagement.domain.TransactionDetails;
import com.ylizma.bankmanagement.domain.TransferDetails;
import com.ylizma.bankmanagement.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Object> getByAccountNumber(@PathVariable Long accountNumber) {
        return bankingService.findByAccountNumber(accountNumber);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addNewAccount(@RequestBody AccountCustomerInfo accountInformation) {
        return bankingService.addNewAccount(accountInformation);
    }

    @GetMapping("/all")
    public List<AccountInformation> getAllAccounts() {
        return bankingService.findAllAccounts();
    }

    @GetMapping("/customer/{customerNumber}")
    public AccountInformation getAccountByCustomer(@PathVariable Long customerNumber) {
        return bankingService.findAccountByCustomerNumber(customerNumber);
    }

    @PutMapping(path = "/transfer/{customerNumber}")
    public ResponseEntity<Object> transferDetails(@RequestBody TransferDetails transferDetails,
                                                  @PathVariable Long customerNumber) {

        return bankingService.transferDetails(transferDetails, customerNumber);
    }

    @GetMapping(path = "/transactions/{accountNumber}")
    public List<TransactionDetails> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

        return bankingService.findTransactionsByAccountNumber(accountNumber);
    }
}
