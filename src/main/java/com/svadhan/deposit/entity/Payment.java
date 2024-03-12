package com.svadhan.deposit.entity;

import com.svadhan.deposit.model.DepositRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;
    private String referenceId;
    private String screenshot;
    private String remarks;

    public Payment() {
    }

    public Payment(DepositRequest depositRequest) {
        this.bankName = depositRequest.getBankName();
        this.referenceId = depositRequest.getReferenceId();
        this.screenshot = depositRequest.getScreenshot();
        this.remarks = depositRequest.getRemarks();
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