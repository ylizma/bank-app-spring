package com.ylizma.bankmanagement.service;

import java.util.List;
import com.ylizma.bankmanagement.domain.CustomerDetails;
import org.springframework.http.ResponseEntity;

public interface BankingService {

    public List<CustomerDetails> findAll();

    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);

    public CustomerDetails findByCustomerNumber(Long customerNumber);

    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);

    public ResponseEntity<Object> deleteCustomer(Long customerNumber) ;

}
