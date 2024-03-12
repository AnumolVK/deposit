package com.svadhan.deposit.exception.customexception;

public class LenderNotAvailableException extends RuntimeException{
    public LenderNotAvailableException(String message) {
        super(message);
    }
}
