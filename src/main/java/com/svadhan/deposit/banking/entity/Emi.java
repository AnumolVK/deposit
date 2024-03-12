package com.svadhan.deposit.banking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "emi")
@SequenceGenerator(name = "emi_generator", sequenceName = "emi_seq", allocationSize = 1)
public class Emi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emi_generator")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "loan_id", nullable = false)
    private Long loanId;
    @Column(name = "installment_no")
    private Long installmentNo;
    @Column(name = "due_amount")
    private Long dueAmount;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "dpd")
    private Integer Dpd;
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "status")
    private String status;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

}