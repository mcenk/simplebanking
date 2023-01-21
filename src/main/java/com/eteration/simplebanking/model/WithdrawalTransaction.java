package com.eteration.simplebanking.model;


import com.eteration.simplebanking.services.ApprovalCodeService;

import javax.persistence.Entity;
import java.time.LocalDateTime;
@Entity
public class WithdrawalTransaction extends Transaction {


    private LocalDateTime date;
    private double amount;
    private String type;
    private String approvalCode;

    public WithdrawalTransaction(double amount) {
        this.setDate(LocalDateTime.now());
        this.setAmount(amount);
        this.setApprovalCode(ApprovalCodeService.generateCode());
        this.setType(getClass().getSimpleName());
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


