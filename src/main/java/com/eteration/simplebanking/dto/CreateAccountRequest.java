package com.eteration.simplebanking.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;


public class CreateAccountRequest {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String owner;
    private double balance;

    public CreateAccountRequest(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance=0;
    }

    public CreateAccountRequest() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
