package com.svadhan.deposit.exception.customexception;

public class RequiredEntityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RequiredEntityNotFoundException() {
        super();
    }

    public RequiredEntityNotFoundException(String message) {
        super(message);
    }
}
