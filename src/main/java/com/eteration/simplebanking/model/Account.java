package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.response.AccountDto;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private String owner;

    // PARASAL ISLEMLER ICIN BIGDECIMAL DAHA UYGUN (ORNEK:BITCOIN ISLEMLERI)
    // ISTERLERE UYGUN SEKILDE double VERILDI
    private double balance;
    @CreatedDate
    private LocalDateTime createDate;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new HashSet<>();

    }

    public Account() {
    }

    public void post(Transaction transaction) {
        if (this.transactions == null)
            this.transactions = new HashSet<>();

        if (transaction.getType().equals("DepositTransaction"))
            deposit(transaction.getAmount());

        if (transaction.getType().equals("WithdrawalTransaction"))
            withdraw(transaction.getAmount());


        this.transactions.add(transaction);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) { // FAILED FIRST
            throw new InsufficientBalanceException("Insufficient balance in account.");
        }
        balance -= amount;
    }


    // GETTER SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

}


