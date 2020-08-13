package com.ylizma.bankmanagement.controller;

import com.ylizma.bankmanagement.domain.AccountInformation;
import com.ylizma.bankmanagement.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> addNewAccount(@RequestBody AccountInformation accountInformation) {
//        return bankingService.addNewAccount(accountInformation);
        return null;
    }
}
