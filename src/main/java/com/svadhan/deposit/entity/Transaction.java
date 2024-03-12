package com.svadhan.deposit.entity;


import com.svadhan.deposit.constants.LoanPaidBy;
import com.svadhan.deposit.constants.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    private static final long serialVersionUID = 6277136197756469530L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "narration")
    private String narration;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_on")
    @UpdateTimestamp
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @ManyToOne()
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatusMaster transactionStatus;

    @ManyToOne
    @JoinColumn(name = "pgw_id")
    private PGWMaster pgw;

    @Column(name = "paid_by")
    @Enumerated(EnumType.STRING)
    private LoanPaidBy paidBy;

    @Column(name = "is_due_amount")
    private Boolean isDueAmount;
}