package com.svadhan.deposit.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ErrorMessage {

    private String message;

    private LocalDateTime timeStamp;

    private String stackTrace;

    public ErrorMessage(String message, LocalDateTime timeStamp, Exception e) {
        this.message = message;
        this.timeStamp = timeStamp;
        if (e != null) {
            this.stackTrace = Arrays.stream(e.getStackTrace()).map(Objects::toString).collect(Collectors.joining(System.lineSeparator()));
        }
    }
}

