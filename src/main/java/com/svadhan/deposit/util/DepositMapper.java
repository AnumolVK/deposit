package com.svadhan.deposit.util;

import com.svadhan.deposit.entity.Payment;
import com.svadhan.deposit.model.DepositRequest;

public class DepositMapper {

    public static Payment mapToPaymentEntity(DepositRequest depositRequest) {
        Payment payment = new Payment();
        payment.setBankName(depositRequest.getBankName());
        payment.setReferenceId(depositRequest.getReferenceId());
        payment.setScreenshot(depositRequest.getScreenshot());
        payment.setRemarks(depositRequest.getRemarks());
        return payment;
    }

    public static DepositRequest mapToDepositRequest(Payment payment) {
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setBankName(payment.getBankName());
        depositRequest.setReferenceId(payment.getReferenceId());
        depositRequest.setScreenshot(payment.getScreenshot());
        depositRequest.setRemarks(payment.getRemarks());
        return depositRequest;
    }
}