package com.svadhan.deposit.repository;

import com.svadhan.deposit.entity.Customer;
import com.svadhan.deposit.entity.Loan;
import com.svadhan.deposit.entity.LoanStatusMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByCustomer(Customer customer);

    List<Loan> findAllByCustomerAndLoanStatus(Customer customer, LoanStatusMaster loanStatus);

    List<Loan> findAllByLenderLoanIdIn(List<String> lenderLoanIds);
}
