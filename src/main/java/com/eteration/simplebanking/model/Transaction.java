package com.eteration.simplebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected LocalDateTime date;
    protected double amount;
    protected String type;
    protected String approvalCode;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    protected Account account;

    public Transaction() {
    }

    public Transaction(Long id, LocalDateTime date, double amount, String type, String approvalCode, Account account) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.approvalCode = approvalCode;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}


