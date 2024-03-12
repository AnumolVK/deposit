package com.svadhan.deposit.entity;


import com.svadhan.deposit.constants.PgwName;
import com.svadhan.deposit.entity.TransactionStatusMaster;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pgw_master")
public class PGWMaster implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Serial
    private static final long serialVersionUID = 6362251276969589957L;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "pgw_name")
    private PgwName name;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "pgw", fetch = FetchType.EAGER)
    private Set<TransactionStatusMaster> transactionStatuses = new LinkedHashSet<>();
}