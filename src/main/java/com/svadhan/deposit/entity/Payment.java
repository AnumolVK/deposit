package com.svadhan.deposit.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "screenshot")
    private String screenshot;

    @Column(name = "remarks")
    private String remarks;

    // Constructors, getters, and setters

    public Payment() {
    }

    public Payment(Long id, String bankName, String referenceId, String screenshot, String remarks) {
        this.id = id;
        this.bankName = bankName;
        this.referenceId = referenceId;
        this.screenshot = screenshot;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
