package com.ylizma.bankmanagement.service.helper;

import com.ylizma.bankmanagement.domain.*;
import com.ylizma.bankmanagement.model.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BankingServiceHelper {

	public CustomerDetails convertToCustomerDomain(Customer customer) {
		
		return CustomerDetails.builder()
				.firstName(customer.getFirstName())
				.middleName(customer.getMiddleName())
				.lastName(customer.getLastName())
				.customerNumber(customer.getCustomerNumber())
				.status(customer.getStatus())
				.contactDetails(convertToContactDomain(customer.getContactDetails()))
				.customerAddress(convertToAddressDomain(customer.getCustomerAddress()))
				.build();
	}
	
	public Customer convertToCustomerEntity(CustomerDetails customerDetails) {
		
		
		return Customer.builder()
				.firstName(customerDetails.getFirstName())
				.middleName(customerDetails.getMiddleName())
				.lastName(customerDetails.getLastName())
				.customerNumber(customerDetails.getCustomerNumber())
				.status(customerDetails.getStatus())
				.contactDetails(convertToContactEntity(customerDetails.getContactDetails()))
				.customerAddress(convertToAddressEntity(customerDetails.getCustomerAddress()))
				.build();
	}

	public AccountInformation convertToAccountDomain(Account account) {

		return AccountInformation.builder()
				.accountType(account.getAccountType())
				.accountBalance(account.getAccountBalance())
				.accountNumber(account.getAccountNumber())
				.accountStatus(account.getAccountStatus())
				.customerDetails(convertToCustomerDomain(account.getCustomer()))
				.bankInformation(convertToBankInfoDomain(account.getBankInformation()))
				.build();
	}
	
	public Account convertToAccountEntity(AccountInformation accInfo) {
		
		return Account.builder()
				.accountType(accInfo.getAccountType())
				.accountBalance(accInfo.getAccountBalance())
				.accountNumber(accInfo.getAccountNumber())
				.accountStatus(accInfo.getAccountStatus())
				.customer(convertToCustomerEntity(accInfo.getCustomerDetails()))
				.bankInformation(convertToBankInfoEntity(accInfo.getBankInformation()))
				.build();
	}

	public Account convertToAccountCustomerEntity(AccountCustomerInfo accountCustomerInfo,Customer customer){
		return Account.builder()
				.accountType(accountCustomerInfo.getAccountType())
				.accountBalance(accountCustomerInfo.getAccountBalance())
				.accountNumber(accountCustomerInfo.getAccountNumber())
				.accountStatus(accountCustomerInfo.getAccountStatus())
				.bankInformation(convertToBankInfoEntity(accountCustomerInfo.getBankInformation()))
				.customer(customer)
				.build();
	}
	
	public AddressDetails convertToAddressDomain(Address address) {
		
		return AddressDetails.builder().address1(address.getAddress1())
				.address2(address.getAddress2())
				.city(address.getCity())
				.state(address.getState())
				.zip(address.getZip())
				.country(address.getCountry())
				.build();
	}
	
	public Address convertToAddressEntity(AddressDetails addressDetails) {
		
		return Address.builder().address1(addressDetails.getAddress1())
				.address2(addressDetails.getAddress2())
				.city(addressDetails.getCity())
				.state(addressDetails.getState())
				.zip(addressDetails.getZip())
				.country(addressDetails.getCountry())
				.build();
	}
	
	public ContactDetails convertToContactDomain(Contact contact) {
		
		return ContactDetails.builder()
				.emailId(contact.getEmailId())
				.homePhone(contact.getHomePhone())
				.workPhone(contact.getWorkPhone())
				.build();
	}
	
	public Contact convertToContactEntity(ContactDetails contactDetails) {
		
		return Contact.builder()
				.emailId(contactDetails.getEmailId())
				.homePhone(contactDetails.getHomePhone())
				.workPhone(contactDetails.getWorkPhone())
				.build();
	}
	
	public BankInformation convertToBankInfoDomain(BankInfo bankInfo) {
		
		return BankInformation.builder()
				.branchCode(bankInfo.getBranchCode())
				.branchName(bankInfo.getBranchName())
				.routingNumber(bankInfo.getRoutingNumber())
				.branchAddress(convertToAddressDomain(bankInfo.getBranchAddress()))
				.build();
	}
	
	public BankInfo convertToBankInfoEntity(BankInformation bankInformation) {

		return BankInfo.builder()
				.branchCode(bankInformation.getBranchCode())
				.branchName(bankInformation.getBranchName())
				.routingNumber(bankInformation.getRoutingNumber())
				.branchAddress(convertToAddressEntity(bankInformation.getBranchAddress()))
				.build();
	}
	public Transaction createTransaction(TransferDetails transferDetails, Account accountNumber, TransactionType txType) {
		return Transaction.builder()
							.accountNumber(accountNumber)
							.txAmount(transferDetails.getTransferAmount())
							.txType(txType)
							.txDateTime(new Date())
							.build();
	}
		public Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {

		return Transaction.builder()
							.txAmount(transactionDetails.getTxAmount())
							.txDateTime(transactionDetails.getTxDateTime())
							.txType(transactionDetails.getTxType())
//							.accountNumber(account)
							.build();
	}
		public TransactionDetails convertToTransactionDomain(Transaction transaction,Account account) {

		return TransactionDetails.builder()
									.txAmount(transaction.getTxAmount())
									.txDateTime(transaction.getTxDateTime())
									.txType(transaction.getTxType())
									.accountInformation(convertToAccountDomain(account))
									.build();
	}
}
