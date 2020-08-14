package com.ylizma.bankmanagement.controller;

import com.ylizma.bankmanagement.domain.CustomerDetails;
import com.ylizma.bankmanagement.service.BankingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private BankingServiceImpl bankingService;

    @PostMapping("/add")
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDetails customerDetails) {
        return bankingService.addCustomer(customerDetails);
    }

    @GetMapping("/all")
    public List<CustomerDetails> getAllCustomers() {
        return bankingService.findAllCustomers();
    }

    @GetMapping("/{customerNumber}")
    public CustomerDetails getCustomer(@PathVariable Long customerNumber) {
        return bankingService.findByCustomerNumber(customerNumber);
    }

    @PutMapping("/{customerNumber}")
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDetails customerDetails, @PathVariable Long customerNumber) {
        return bankingService.updateCustomer(customerDetails, customerNumber);
    }

    @DeleteMapping("/{customerNumber}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerNumber) {
        return bankingService.deleteCustomer(customerNumber);
    }

}
