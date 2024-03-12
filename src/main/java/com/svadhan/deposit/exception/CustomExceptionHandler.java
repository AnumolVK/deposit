package com.svadhan.deposit.exception;

import com.svadhan.deposit.exception.customexception.LenderNotAvailableException;
import com.svadhan.deposit.exception.customexception.RequiredEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RequiredEntityNotFoundException.class)
    public ResponseEntity<?> handleRequiredEntityNotFoundException(RequiredEntityNotFoundException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), LocalDateTime.now(), e),
                HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(UnableToReadTheDetailsException.class)
//    public ResponseEntity<?> handleUnableToReadTheDetailsException(UnableToReadTheDetailsException e) {
//        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), LocalDateTime.now(), e),
//                HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(DeviceIdMismatchException.class)
//    public ResponseEntity<?> handleDeviceIdMismatchException(DeviceIdMismatchException e) {
//        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), LocalDateTime.now(), e),
//                HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), LocalDateTime.now(), e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LenderNotAvailableException.class)
    public ResponseEntity<?> handleLenderNotAvailableException(LenderNotAvailableException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), LocalDateTime.now(), e),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage(), LocalDateTime.now(), e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
