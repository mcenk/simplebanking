package com.eteration.simplebanking.services;

import com.eteration.simplebanking.request.UpdateAccountRequest;
import com.eteration.simplebanking.response.TransactionStatus;
import com.eteration.simplebanking.response.AccountDto;
import com.eteration.simplebanking.converter.AccountDtoConverter;
import com.eteration.simplebanking.request.CreateAccountRequest;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.DuplicateAccountException;
import com.eteration.simplebanking.exception.TransactionFailedException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import lombok.Synchronized;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final ApprovalCodeService codeService;
    private final AccountDtoConverter accountDtoConverter;

    public AccountService(AccountRepository accountRepository, TransactionService transactionService, ApprovalCodeService codeService, AccountDtoConverter accountDtoConverter) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.codeService = codeService;
        this.accountDtoConverter = accountDtoConverter;
    }

    @Synchronized
    @Transactional(propagation = Propagation.REQUIRED)
    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {

        Optional<Account> customer = accountRepository.findByAccountNumber(createAccountRequest.getAccountNumber());
        if (customer.isPresent()) {
            throw new DuplicateAccountException("Allready have a account with" + createAccountRequest.getAccountNumber());
        }

        Account account = new Account();
        account.setOwner(createAccountRequest.getOwner());
        account.setAccountNumber(createAccountRequest.getAccountNumber());
        account.setCreateDate(LocalDateTime.now());


        // ILK KAYIT SIRASINDA BALANCE DEGERI GELIRSE ILK TRANSACTION YARATILACAK

        if (createAccountRequest.getBalance() > 0) {
            Transaction transaction = new DepositTransaction(createAccountRequest.getBalance());
            transaction.setAccount(account);
            transaction.setDate(LocalDateTime.now());
            transaction.setAmount(createAccountRequest.getBalance());
            transaction.setApprovalCode(ApprovalCodeService.generateCode());
            transaction.setType(transaction.getClass().getSimpleName());
            transactionService.createTransaction(transaction);
            account.post(transaction);
        }
        Account savedAccount = accountRepository.save(account);

        return accountDtoConverter.converter(savedAccount);

    }

    public List<AccountDto> getAllAccount() {

        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) throw new AccountNotFoundException("Cannot get all account information!");

        return accounts.stream().map(accountDtoConverter::converter).collect(Collectors.toList());
    }

    public AccountDto findAccount(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountDtoConverter::converter)
                .orElseThrow(() -> new AccountNotFoundException("Account could not find by id:" + accountNumber));
    }


    @Synchronized
    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionStatus credit(String accountNumber, Transaction transaction) throws TransactionFailedException {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account could not find by id:" + accountNumber));

        if (transaction.getAmount() < 0) throw new TransactionFailedException("Transaction failed!");

        transaction = new DepositTransaction(transaction.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setApprovalCode(ApprovalCodeService.generateCode());
        transaction.setType(transaction.getClass().getSimpleName());
        transaction.setAccount(account);
        transactionService.createTransaction(transaction);
        account.post(transaction);
        accountRepository.save(account);

        return new TransactionStatus("OK", ApprovalCodeService.generateCode());


    }

    @Synchronized
    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionStatus debit(String accountNumber, Transaction transaction) throws TransactionFailedException {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account could not find by id:" + accountNumber));

        if (transaction.getAmount() < 0) throw new TransactionFailedException("Transaction failed!");

        transaction = new WithdrawalTransaction(transaction.getAmount());
        transaction.setDate(LocalDateTime.now());
        transaction.setApprovalCode(ApprovalCodeService.generateCode());
        transaction.setType(transaction.getClass().getSimpleName());
        transaction.setAccount(account);
        transactionService.createTransaction(transaction);
        account.post(transaction);
        accountRepository.save(account);
        return new TransactionStatus("OK", ApprovalCodeService.generateCode());

    }

    public AccountDto updateAccount(String accountNumber, UpdateAccountRequest request) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account could not find by id:" + accountNumber));

        account.setBalance(request.getBalance());
        account.setCreateDate(LocalDateTime.now());
        account.setAccountNumber(request.getAccountNumber());
        accountRepository.save(account);

        return accountDtoConverter.converter(account);
    }

    public void deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account could not find by id:" + accountNumber));
        accountRepository.deleteById(account.getId());
    }
}
