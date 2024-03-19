package com.example.bettingApplication.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Insufficient funds in the wallet.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}