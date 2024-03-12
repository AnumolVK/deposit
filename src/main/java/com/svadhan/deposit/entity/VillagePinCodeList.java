package com.svadhan.deposit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "village_pin_code_list")
public class VillagePinCodeList implements Serializable {
    private static final long serialVersionUID = 4821792462357412003L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "is_deleted")
    private Integer isDeleted;
    @Column(name = "deleted_on")
    private LocalDateTime deletedOn;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @Column(name = "local_body_id")
    private Integer localBodyId;
    @Column(name = "village_name")
    private String villageName;
    @Column(name = "pin_code")
    private String pinCode;
}