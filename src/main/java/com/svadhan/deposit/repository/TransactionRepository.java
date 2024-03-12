package com.svadhan.deposit.repository;


import com.svadhan.deposit.constants.LoanPaidBy;
import com.svadhan.deposit.constants.TransactionType;
import com.svadhan.deposit.entity.Loan;
import com.svadhan.deposit.entity.Transaction;
import com.svadhan.deposit.entity.TransactionStatusMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByLoan(Loan loan);
    List<Transaction> findAllByLoanAndTypeAndTransactionStatusAndNarrationIn(Loan loan, TransactionType type, TransactionStatusMaster transactionStatus, List<String> narrations);

    List<Transaction> findAllByLoanAndTransactionStatusAndPaidBy(Loan loan, TransactionStatusMaster transactionStatus, LoanPaidBy paidBy);
    List<Transaction> findAllByLoanAndTransactionStatusAndPgwNotNull(Loan loan, TransactionStatusMaster transactionStatus);
    List<Transaction> findAllByLoanAndTransactionStatusAndPgwNull(Loan loan, TransactionStatusMaster transactionStatus);

    //New added query for the requirement change - Need to modify it later
    @Query(nativeQuery = true, value = "SELECT * FROM TRANSACTION t WHERE t.ID = :ids AND t.PGW_ID IS NULL")
    Optional<Transaction> findTransactionById(@Param("ids") Long ids);
}
