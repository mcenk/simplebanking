package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {

    public AccountDto converter(Account account){
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .owner(account.getOwner())
                .balance(account.getBalance())
                .createDate(account.getCreateDate())
                .transactions(account.getTransactions())
                .build();
    }
}
