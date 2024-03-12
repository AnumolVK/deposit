package com.svadhan.deposit.model;

import lombok.Data;

@Data
public class DepositDetailsDTO {

    private Long customerId;
    private String customerName;
    private String village;
    private String mobileNumber;
    private Double amount;
    private boolean isDuePending;
    private boolean isDueDatePassed;

}
