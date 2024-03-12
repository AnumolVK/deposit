package com.svadhan.deposit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "customer")
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = -1791822571279021916L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Id
//    @Column(name="customer_id")
//    @GenericGenerator(
//            name="MY_SEQ",
//            strategy= "com.svadhan.customeronboarding.customidgenerator.CustomerIdGenerator")
//    @GeneratedValue(generator="MY_SEQ")
//    private String orderId;


    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "sim_id")
    private String simId;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "device_ip_address")
    private String deviceIpAddress;

    @Column(name = "has_register_process_completed")
    private Boolean hasRegisterProcessCompleted;

    @Column(name = "rejection_date_time")
    private LocalDateTime rejectionDateTime;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp()
    private LocalDateTime createDateTime;

    @Column(name = "modified_on", nullable = false)
    @UpdateTimestamp()
    private LocalDateTime modifiedOn;

    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
    private CustomerOcrData customerOcrData;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "token_timestamp")
    private LocalDateTime tokenTimestamp;

    @Transient
    private String pin;

    @Column(name = "continuing_guarantor_id")
    private Long continuingGuarantorId;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "associated_agent_id")
    private Long associatedAgentId;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerBasicDetail customerBasicDetail;
}
