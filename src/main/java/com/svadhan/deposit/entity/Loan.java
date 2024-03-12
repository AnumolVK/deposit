package com.svadhan.deposit.entity;

import com.svadhan.deposit.entity.Customer;
import com.svadhan.deposit.entity.LenderMaster;
import com.svadhan.deposit.entity.LoanStatusMaster;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "loan")
@Table()
@SequenceGenerator(name = "loan_generator", sequenceName = "loan_seq", allocationSize = 1)
public class Loan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private LenderMaster lender;

    @Column(name = "lender_loan_id")
    private String lenderLoanId;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "disbursed_on")
    private LocalDateTime disbursedOn;

    @Column(name = "tenor")
    private Integer tenor;

    @Column(name = "emi")
    private Double emi;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    @UpdateTimestamp
    private LocalDateTime modifiedOn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_status_id", nullable = false)
    private LoanStatusMaster loanStatus;

    @Column(name = "loan_application_id")
    private String loanApplicationId;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "interest_amount")
    private Double interestAmount;

    @Column(name = "emi_start_date")
    private LocalDateTime emiStartDate;

    @Column(name = "processing_fee")
    private Double processingFee;

    @Column(name = "gst_on_processing_fee")
    private Double gstOnProcessingFee;

    @Column(name = "cess_on_processing_fee")
    private Double cessOnProcessingFee;

    @Column(name = "customer_credit_insurance")
    private Double customerCreditInsurance;

    @Column(name = "guarantor_credit_insurance")
    private Double guarantorCreditInsurance;

    @Column(name = "no_of_instalments")
    private Integer noOfInstalments;

    @Column(name = "total_amount_to_be_paid_by_customer")
    private Double totalAmountToBePaidByCustomer;

    @Column(name = "effective_annualized_interest_rate")
    private Double effectiveAnnualizedInterestRate;

    @Column(name = "penalinterest_rate")
    private Double penalInterestRate;

    @Column(name = "agreement_url_eng")
    private String agreementUrlEng;

    @Column(name = "agreement_url_ml")
    private String agreementUrlMl;

    @Column(name = "is_aggrement_signed")
    private Boolean isAggrementSigned;

    @Column(name = "net_disbursement_amount")
    private Double netDisbursementAmount;

    @Column(name = "lender_customer_id")
    private String lenderCustomerId;
}