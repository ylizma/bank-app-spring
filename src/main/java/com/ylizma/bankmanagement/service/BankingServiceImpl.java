package com.ylizma.bankmanagement.service;

import com.ylizma.bankmanagement.domain.*;
import com.ylizma.bankmanagement.model.*;
import com.ylizma.bankmanagement.repository.AccountRepository;
import com.ylizma.bankmanagement.repository.CustomerRepository;
import com.ylizma.bankmanagement.repository.TransactionRepository;
import com.ylizma.bankmanagement.service.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BankingServiceImpl implements BankingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

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

    @Override
    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber) {

        List<Account> accountEntities = new ArrayList<>();
        Account fromAccountEntity = null;
        Account toAccountEntity = null;

        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        // If customer is present
        if (customerEntityOpt.isPresent()) {

            // get FROM ACCOUNT info
            Optional<Account> fromAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getFromAccountNumber());
            if (fromAccountEntityOpt.isPresent()) {
                if (!fromAccountEntityOpt.get().getCustomer().equals(customerEntityOpt.get())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("the customer number doesn't match the account number!");
                } else {
                    fromAccountEntity = fromAccountEntityOpt.get();
                }
            } else {
                // if from request does not exist, 404 Bad Request
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + transferDetails.getFromAccountNumber() + " not found.");
            }

            // get TO ACCOUNT info
            Optional<Account> toAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getToAccountNumber());
            if (toAccountEntityOpt.isPresent()) {
                toAccountEntity = toAccountEntityOpt.get();
            } else {
                // if from request does not exist, 404 Bad Request
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("To Account Number " + transferDetails.getToAccountNumber() + " not found.");
            }


            // if not sufficient funds, return 400 Bad Request
            if (fromAccountEntity.getAccountBalance().compareTo(transferDetails.getTransferAmount()) < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
            } else {
                synchronized (this) {
                    // update FROM ACCOUNT
                    fromAccountEntity.setAccountBalance(fromAccountEntity.getAccountBalance().subtract(transferDetails.getTransferAmount()));
                    fromAccountEntity.setUpdateDateTime(new Date());
                    accountEntities.add(fromAccountEntity);

                    // update TO ACCOUNT
                    toAccountEntity.setAccountBalance(toAccountEntity.getAccountBalance().add(transferDetails.getTransferAmount()));
                    toAccountEntity.setUpdateDateTime(new Date());
                    accountEntities.add(toAccountEntity);

                    accountRepository.saveAll(accountEntities);

                    // Create transaction for FROM Account
                    Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetails, fromAccountEntity, TransactionType.DEBIT);
                    transactionRepository.save(fromTransaction);

                    // Create transaction for TO Account
                    Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetails, toAccountEntity, TransactionType.CREDIT);
                    transactionRepository.save(toTransaction);
                }

                return ResponseEntity.status(HttpStatus.OK).body("Success: Amount transferred for Customer Number " + customerNumber);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
        }
    }

    @Override
    public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber) {
        List<TransactionDetails> transactionDetailsList = new ArrayList<>();
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);;
        if (account.isPresent()) {
            System.out.println(account.get().toString());
            List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
//            System.out.println(Arrays.toString(transactions.get().toArray()));
                    transactions.forEach(transaction -> transactionDetailsList
                            .add(bankingServiceHelper.convertToTransactionDomain(transaction, account.get())));
        }
        return transactionDetailsList;
    }

}
