package com.eteration.simplebanking.model;


import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(double amount) {
        this.amount = amount;

    }

    public WithdrawalTransaction() {

    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getApprovalCode() {
        return approvalCode;
    }

    @Override
    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
}


