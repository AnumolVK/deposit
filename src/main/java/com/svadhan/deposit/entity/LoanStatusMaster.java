package com.svadhan.deposit.entity;


import com.svadhan.deposit.constants.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "loan_status_master")
public class LoanStatusMaster implements Serializable {
    @Serial
    private static final long serialVersionUID = 1374817593232818700L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "status_label")
    private String statusLabel;

}