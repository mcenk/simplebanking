package com.eteration.simplebanking.response;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String accountNumber;

    private String owner;

    private double balance;

    private LocalDateTime createDate;

    private Set<Transaction> transactions;

    public AccountDto(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
    }
}
