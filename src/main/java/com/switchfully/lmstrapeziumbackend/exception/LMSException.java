package com.switchfully.lmstrapeziumbackend.exception;

public class LMSException extends RuntimeException {

    private int statusCode;

    public LMSException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
