package com.svadhan.deposit.model;

import lombok.Data;

@Data
public class DepositDetailsRequest {
    String enityName;
    String transactionId;
    String Screenshot;
    String remarks;
}
