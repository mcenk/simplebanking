package com.eteration.simplebanking.exception;

public class TransactionFailedException extends RuntimeException{

    public TransactionFailedException(String message) {
        super(message);
    }
}
