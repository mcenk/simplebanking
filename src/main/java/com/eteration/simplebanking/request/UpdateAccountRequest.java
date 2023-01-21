package com.eteration.simplebanking.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class UpdateAccountRequest {

    @NotBlank
    @Size(min = 5, max = 12)
    private String accountNumber;
    @NotBlank
    private String owner;
    @Min(value = 0)
    private double balance;
    private LocalDateTime createDate;

    public UpdateAccountRequest(String accountNumber, String owner, double balance, LocalDateTime createDate) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.createDate = createDate;
    }

    public UpdateAccountRequest() {
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
