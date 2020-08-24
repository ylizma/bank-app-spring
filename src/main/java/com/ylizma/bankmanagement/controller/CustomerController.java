package com.ylizma.bankmanagement.controller;

import com.ylizma.bankmanagement.domain.CustomerDetails;
import com.ylizma.bankmanagement.service.BankingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Customer REST endpoints")
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private BankingServiceImpl bankingService;

    @PostMapping("/add")
    @ApiOperation(value = "Add a new Customer", notes = "Add customer to create an account")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDetails customerDetails) {
        return bankingService.addCustomer(customerDetails);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Find all customers", notes = "Gets details of all the customers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public List<CustomerDetails> getAllCustomers() {
        return bankingService.findAllCustomers();
    }

    @GetMapping("/{customerNumber}")
    @ApiOperation(value = "Get customer details", notes = "Get Customer details by customer number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CustomerDetails.class, responseContainer = "Object"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public CustomerDetails getCustomer(@PathVariable Long customerNumber) {
        return bankingService.findByCustomerNumber(customerNumber);
    }

    @PutMapping("/{customerNumber}")
    @ApiOperation(value = "Update customer", notes = "Update customer and any other account information associated with him.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDetails customerDetails, @PathVariable Long customerNumber) {
        return bankingService.updateCustomer(customerDetails, customerNumber);
    }

    @DeleteMapping("/{customerNumber}")
    @ApiOperation(value = "Delete customer and related accounts", notes = "Delete customer and all accounts associated with him.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerNumber) {
        return bankingService.deleteCustomer(customerNumber);
    }

}
