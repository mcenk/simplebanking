package com.eteration.simplebanking.exception;

public class DuplicateAccountException extends RuntimeException{

    public DuplicateAccountException(String message) {
        super(message);
    }
}
