package com.ylizma.bankmanagement.service;

import com.ylizma.bankmanagement.domain.AccountCustomerInfo;
import com.ylizma.bankmanagement.domain.AccountInformation;
import com.ylizma.bankmanagement.domain.CustomerDetails;
import com.ylizma.bankmanagement.model.Account;
import com.ylizma.bankmanagement.model.Address;
import com.ylizma.bankmanagement.model.Contact;
import com.ylizma.bankmanagement.model.Customer;
import com.ylizma.bankmanagement.repository.AccountRepository;
import com.ylizma.bankmanagement.repository.CustomerRepository;
import com.ylizma.bankmanagement.service.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankingServiceImpl implements BankingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<CustomerDetails> findAllCustomers() {
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();
        Iterable<Customer> customerList = customerRepository.findAll();
        customerList.forEach(customer ->
                allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer))
        );
        return allCustomerDetails;
    }

    @Override
    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {
        CustomerDetails checkCustomer = findByCustomerNumber(customerDetails.getCustomerNumber());
        if (checkCustomer != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer with the Number " + customerDetails.getCustomerNumber() + " Exist Already");
        } else {
            Customer newCostomer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
            customerRepository.save(newCostomer);
            return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
        }
    }

    @Override
    public CustomerDetails findByCustomerNumber(Long customerNumber) {
        Optional<Customer> customer = customerRepository.findByCustomerNumber(customerNumber);
        return customer.map(value -> bankingServiceHelper.convertToCustomerDomain(value)).orElse(null);
    }

    @Override
    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber) {
        Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        Customer unmanagedCustomerEntity = bankingServiceHelper.convertToCustomerEntity(customerDetails);
        if (managedCustomerEntityOpt.isPresent()) {
            Customer managedCustomerEntity = managedCustomerEntityOpt.get();

            if (Optional.ofNullable(unmanagedCustomerEntity.getContactDetails()).isPresent()) {

                Contact managedContact = managedCustomerEntity.getContactDetails();
                if (managedContact != null) {
                    managedContact.setEmailId(unmanagedCustomerEntity.getContactDetails().getEmailId());
                    managedContact.setHomePhone(unmanagedCustomerEntity.getContactDetails().getHomePhone());
                    managedContact.setWorkPhone(unmanagedCustomerEntity.getContactDetails().getWorkPhone());
                } else
                    managedCustomerEntity.setContactDetails(unmanagedCustomerEntity.getContactDetails());
            }

            if (Optional.ofNullable(unmanagedCustomerEntity.getCustomerAddress()).isPresent()) {

                Address managedAddress = managedCustomerEntity.getCustomerAddress();
                if (managedAddress != null) {
                    managedAddress.setAddress1(unmanagedCustomerEntity.getCustomerAddress().getAddress1());
                    managedAddress.setAddress2(unmanagedCustomerEntity.getCustomerAddress().getAddress2());
                    managedAddress.setCity(unmanagedCustomerEntity.getCustomerAddress().getCity());
                    managedAddress.setState(unmanagedCustomerEntity.getCustomerAddress().getState());
                    managedAddress.setZip(unmanagedCustomerEntity.getCustomerAddress().getZip());
                    managedAddress.setCountry(unmanagedCustomerEntity.getCustomerAddress().getCountry());
                } else
                    managedCustomerEntity.setCustomerAddress(unmanagedCustomerEntity.getCustomerAddress());
            }

            managedCustomerEntity.setUpdateDateTime(new Date());
            managedCustomerEntity.setStatus(unmanagedCustomerEntity.getStatus());
            managedCustomerEntity.setFirstName(unmanagedCustomerEntity.getFirstName());
            managedCustomerEntity.setMiddleName(unmanagedCustomerEntity.getMiddleName());
            managedCustomerEntity.setLastName(unmanagedCustomerEntity.getLastName());
            managedCustomerEntity.setUpdateDateTime(new Date());

            customerRepository.save(managedCustomerEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Success: Customer updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
        }
    }

    @Override
    public ResponseEntity<Object> deleteCustomer(Long customerNumber) {
        Optional<Customer> managedCustomer = customerRepository.findByCustomerNumber(customerNumber);
        if (managedCustomer.isPresent()) {
            customerRepository.delete(managedCustomer.get());
            return ResponseEntity.status(HttpStatus.OK).body("Success: Customer deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not exist.");
        }
    }

    @Override
    public ResponseEntity<Object> findByAccountNumber(Long accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.FOUND).body(bankingServiceHelper.convertToAccountDomain(value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number " + accountNumber + " not found."));
    }

    @Override
    public ResponseEntity<Object> addNewAccount(AccountCustomerInfo accountCustomerInfo) {
        if (accountRepository.findByAccountNumber(accountCustomerInfo.getAccountNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account already exist");
        } else {
            Optional<Customer> customer = customerRepository.findByCustomerNumber(accountCustomerInfo.getCustomerNumber());
            if (customer.isPresent()) {
                if (accountRepository.findByCustomerNumber(accountCustomerInfo.getCustomerNumber()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this customer has already an account");
                } else {
                    Account account = bankingServiceHelper.convertToAccountCustomerEntity(accountCustomerInfo, customer.get());
                    accountRepository.save(account);
                    return ResponseEntity.status(HttpStatus.CREATED).body("New Account created successfully.");
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer doesnt exist");
        }
    }

    @Override
    public List<AccountInformation> findAllAccounts() {
        Iterable<Account> accounts = accountRepository.findAll();
        List<AccountInformation> accountInformationList = new ArrayList<>();
        accounts.forEach(account -> accountInformationList.add(bankingServiceHelper.convertToAccountDomain(account)));
        return accountInformationList;
    }

    @Override
    public AccountInformation findAccountByCustomerNumber(Long customerNumber) {
        Optional<Account> account = accountRepository.findByCustomerNumber(customerNumber);
        return (account.isPresent()) ? (bankingServiceHelper.convertToAccountDomain(account.get())) : null;
    }

}
