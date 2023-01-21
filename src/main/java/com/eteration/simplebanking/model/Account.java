package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.services.TransactionService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public Account( String owner,String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance= 0.0;
        this.transactions= new HashSet<>();

    }
    public Account() {
    }

    public void post(Transaction transaction){
        if (this.transactions == null)
                this.transactions = new HashSet<>();

        if(transaction.getType().equals("DepositTransaction") )
        this.balance += transaction.getAmount();
        else this.balance-=transaction.getAmount();

        this.transactions.add(transaction);}

    public void deposit(double amount){
        if(amount>0){
            this.balance += amount;
        } else throw new RuntimeException(); // COSTUM EXCEPTION EKLE BU ALANA
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance in account.");
            }
            balance -= amount;
        }

        // GETTER SETTER ALANI
        // TEST AMACLI LOMBOK ANOTASYONLARI KALDIRILDI

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


