package com.svadhan.deposit.banking.repository;

import com.svadhan.deposit.banking.entity.Emi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmiRepository extends JpaRepository<Emi, Long> {

    List<Emi> findAllByLoanIdOrderByCreatedOnDesc(Long loanId);

    List<Emi> findAllByLoanIdOrderByCreatedOnAsc(Long loanId);

    List<Emi> findAllByLoanIdAndStatusNotOrderByCreatedOnDesc(Long loanId,String status);

    List<Emi> findAllByLoanIdAndCreatedOnGreaterThanEqual(Long loanId, LocalDateTime createdOn);

    List<Emi> findByLoanIdAndDueDateEquals(Long loanId, LocalDate dueDate);

    Optional<Emi> findByDueDate(LocalDate dueDate);

    List<Emi> findAllByLoanIdAndDueDateBefore(Long loanId,LocalDate dueDate);

    List<Emi> findAllByLoanIdAndStatusNotInAndDueDateBefore(Long loanId, List<String> status, LocalDate date);


    List<Emi> findAllByLoanIdAndStatus(Long loanId, String status);
}
