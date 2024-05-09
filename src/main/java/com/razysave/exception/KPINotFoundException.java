package com.razysave.exception;

public class KPINotFoundException extends RuntimeException {
    public KPINotFoundException(String message) {
        super(message);
    }
}