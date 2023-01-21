package com.eteration.simplebanking.model;

import javax.persistence.*;


@Entity
public class BillPaymentTransaction extends Transaction {


    private String payee;
    private String phoneNumber;
    private double amount;

    public BillPaymentTransaction(String payee, String phoneNumber, double amount) {
        this.payee = payee;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }

    public BillPaymentTransaction() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
