package com.svadhan.deposit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.svadhan.deposit.constants.AccessRoadType;
import com.svadhan.deposit.constants.VerificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "customer_basic_detail")
@SequenceGenerator(name = "customer_basic_detail_generator", sequenceName = "customer_basic_detail_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerBasicDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_basic_detail_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "village")
    private String village;

    @Column(name = "access_road_type")
    @Enumerated(EnumType.STRING)
    private AccessRoadType accessRoadType;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "land_mark")
    private String landMark;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
}
