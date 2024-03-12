package com.svadhan.deposit.model;

import com.svadhan.deposit.entity.Payment;
import lombok.Data;

@Data
public class DepositRequest {
  private String bankName;
    private String referenceId;
    private String screenshot;
    private String remarks;
  public DepositRequest() {
  }

  public DepositRequest(Payment payment) {
    this.bankName = payment.getBankName();
    this.referenceId = payment.getReferenceId();
    this.screenshot = payment.getScreenshot();
    this.remarks = payment.getRemarks();
  }
}

