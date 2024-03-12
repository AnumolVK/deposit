package com.svadhan.deposit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction_status_master")
public class TransactionStatusMaster implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Serial
    private static final long serialVersionUID = -3646452556952298762L;

    @ManyToOne
    @JoinColumn(name = "pgw_id")
    private PGWMaster pgw;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "status")
    private String status;

    @Column(name = "status_label")
    private String statusLabel;

    @Column(name = "is_active")
    private Boolean isActive;
}