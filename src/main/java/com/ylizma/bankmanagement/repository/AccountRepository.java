package com.ylizma.bankmanagement.repository;

import com.ylizma.bankmanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByAccountNumber(Long accountNumber);
		@Query("SELECT a FROM Account a WHERE a.customer.customerNumber = :customerNumber")
	Optional<Account> findByCustomerNumber(@Param("customerNumber") Long customerNumber);

}
