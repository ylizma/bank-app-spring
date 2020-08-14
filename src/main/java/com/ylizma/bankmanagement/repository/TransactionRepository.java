package com.ylizma.bankmanagement.repository;
import com.ylizma.bankmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("select t from Transaction t where t.accountNumber.accountNumber = :accountNumber")
    public List<Transaction> findByAccountNumber(@Param("accountNumber") Long accountNumber);
    
}
