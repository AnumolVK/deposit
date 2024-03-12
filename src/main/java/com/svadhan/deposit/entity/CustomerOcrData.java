package com.svadhan.deposit.entity;

import com.svadhan.deposit.entity.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Entity
@Table(name = "customer_ocr_data")
public class CustomerOcrData implements Serializable {
    @Serial
    private static final long serialVersionUID = -7970115728389936689L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "aadhaar_number")
    private String aadhaarNumber;

    //	VoterId Fields
    @Column(name = "voter_id_name")
    private String voterIdName;

    @Column(name = "voter_id_number")
    private String voterIdNumber;

    @Column(name = "husband_voter_id_name")
    private String husbandVoterIdName;

    @Column(name = "husband_voter_id_number")
    private String husbandVoterIdNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ocr_missing_fields", // table name
            joinColumns = @JoinColumn(name = "customer_ocr_id")// FK Column
    )
    private Set<String> fields;

    @Column(name = "kyc_status")
    private Boolean kycStatus;

}