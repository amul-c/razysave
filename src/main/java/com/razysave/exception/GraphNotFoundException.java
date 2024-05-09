package com.razysave.exception;

public class GraphNotFoundException extends RuntimeException {
    public GraphNotFoundException(String message) {
        super(message);
    }
}