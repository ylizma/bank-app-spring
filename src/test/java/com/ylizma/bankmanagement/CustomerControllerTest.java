package com.ylizma.bankmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylizma.bankmanagement.controller.CustomerController;
import com.ylizma.bankmanagement.domain.AddressDetails;
import com.ylizma.bankmanagement.domain.ContactDetails;
import com.ylizma.bankmanagement.domain.CustomerDetails;
import com.ylizma.bankmanagement.model.Address;
import com.ylizma.bankmanagement.model.Contact;
import com.ylizma.bankmanagement.model.Customer;
import com.ylizma.bankmanagement.service.BankingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;


@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BankingService bankingService;

    CustomerDetails ali;
    AddressDetails aliAddress;
    ContactDetails aliContact;
    CustomerDetails mohammed;
    AddressDetails mohammedAddress;
    ContactDetails mohammedContact;
    String aliJsonString;
    String mohammedJsonString;
    String requestUri = "/bank-api/customers";

    @BeforeAll
    void setup() {
        aliAddress = new AddressDetails("agadir", "tikiouine", "agadir", "agadir", "80500", "morocco");
        aliContact = new ContactDetails("qli@gmail.com", "07676734", "12222");
        ali = new CustomerDetails("ali", "doe", "ali", (long) 1230, "single", aliAddress, aliContact);
        mohammedAddress = new AddressDetails("safi", "zaitoune", "safi", "safi", "84500", "morocco");
        mohammedContact = new ContactDetails("med@gmail.com", "988332", "9000");
        mohammed = new CustomerDetails("mohammed", "med", "mohammed", (long) 3000, "married", mohammedAddress, mohammedContact);
    }

    @Test
    void post_createNewCustomer() {
//        Mockito.when(bankingService.addCustomer(Mockito.any(CustomerDetails.class))).thenReturn(ResponseEntity);
    }

    @Test
    void findAllCustomers() {

    }


}