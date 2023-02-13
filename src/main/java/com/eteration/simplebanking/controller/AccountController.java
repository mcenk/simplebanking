package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.request.UpdateAccountRequest;
import com.eteration.simplebanking.response.AccountDto;
import com.eteration.simplebanking.request.CreateAccountRequest;
import com.eteration.simplebanking.response.TransactionStatus;
import com.eteration.simplebanking.exception.TransactionFailedException;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account/v1")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccount() {

        return ResponseEntity.ok(accountService.getAllAccount());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> findAccount(@Valid @PathVariable String accountNumber) {

        return ResponseEntity.ok(accountService.findAccount(accountNumber));
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction transaction)
            throws TransactionFailedException {

        return ResponseEntity.ok(accountService.credit(accountNumber, transaction));
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {

        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@Valid @PathVariable String accountNumber, @RequestBody DepositTransaction transaction)
            throws TransactionFailedException {

        return ResponseEntity.ok(accountService.debit(accountNumber, transaction));
    }

    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String accountNumber, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(accountNumber, updateAccountRequest ));
    }

    @DeleteMapping("delete/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok("Account deleted!");

    }

}