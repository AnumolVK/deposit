package com.svadhan.deposit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "svadhan_mithra_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "svadhan_mithra_id", nullable = false)
    private String svadhanMithraId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
    @Column(name = "marital_status", nullable = false)
    private Boolean maritalStatus;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "district", nullable = false)
    private String district;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "pin_code", nullable = false)
    private String pinCode;
    @Column(name = "aadhaar_number", nullable = false)
    private String aadhaarNumber;
    @Column(name = "voter_id", nullable = false)
    private String voterId;
    @Column(name = "pan_number")
    private String panNumber;
    @Column(name = "device_token")
    private String deviceToken;
    @Column(name = "token_timestamp")
    private LocalDateTime tokenTimestamp;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "deleted_by")
    private String deletedBy;
    /**
     * Table might change
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "svadhan_mithra_assigned_pin", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "assigned_pin")
    Set<String> assignedPins = new HashSet<>();
}