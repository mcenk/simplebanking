package com.eteration.simplebanking.model;

import com.eteration.simplebanking.services.ApprovalCodeService;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DepositTransaction extends Transaction {


    public DepositTransaction(double amount) {
        this.amount = amount;
    }

    public DepositTransaction() {
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
