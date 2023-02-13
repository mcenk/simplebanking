package com.eteration.simplebanking;

import com.eteration.simplebanking.converter.AccountDtoConverter;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.DuplicateAccountException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.TransactionFailedException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.request.CreateAccountRequest;
import com.eteration.simplebanking.request.UpdateAccountRequest;
import com.eteration.simplebanking.response.AccountDto;
import com.eteration.simplebanking.response.TransactionStatus;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.services.ApprovalCodeService;
import com.eteration.simplebanking.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ApprovalCodeService codeService;
    @Autowired
    private AccountDtoConverter accountDtoConverter;


    @Test
    void whenCreateAccountCalledWithValidRequest_ItShouldReturnAccountDto() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        assertAll("request",
                () -> assertNotNull(result, result.getOwner()),
                () -> assertEquals("17892", result.getAccountNumber()),
                () -> assertEquals("Kerem Karaca", result.getOwner())
        );
    }

    @Test
    void whenCreateAccountCalledWithValidRequestAndDeposit_ItShouldReturnAccountDtoAndTransaction() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        createAccountRequest.setBalance(100);

        AccountDto result = accountService.createAccount(createAccountRequest);

        assertAll("request",
                () -> assertNotNull(result, result.getOwner()),
                () -> assertEquals("17892", result.getAccountNumber()),
                () -> assertEquals(1, result.getTransactions().size())
        );
    }

    @Test
    public void whenCreateAccountCalledWithDuplicateOwner_itShouldThrowDuplicateAccountException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        CreateAccountRequest duplicateRequest = generateAccountRequest();

        assertThrows(DuplicateAccountException.class,
                () -> accountService.createAccount(duplicateRequest));
    }

    @Test
    void whenGetAllAccountCalledWithExistAccount_ItShouldReturnAllAccountDto() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        CreateAccountRequest createAccountRequest2 = generateAccountWithParam("231233", "Mehmet Eter");

        AccountDto result = accountService.createAccount(createAccountRequest);

        AccountDto result2 = accountService.createAccount(createAccountRequest2);

        List<AccountDto> accounts = accountService.getAllAccount();

        assertEquals(2, accounts.size());
    }

    @Test
    void whenGetAllAccountCalledWithNonExistAccount_ItShouldThrowAccountNotFoundException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        assertThrows(AccountNotFoundException.class,
                () -> accountService.getAllAccount());
    }

    @Test
    void whenFindAccountCalledWithExistAccount_ItShouldReturnAccountDto() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        AccountDto accounts = accountService.findAccount(result.getAccountNumber());

        assertEquals("17892", accounts.getAccountNumber());
    }

    @Test
    void whenFindAccountCalledWithNonExistAccount_ItShouldThrowAccountNotFoundException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        assertThrows(AccountNotFoundException.class,
                () -> accountService.findAccount("121212"));
    }


    @Test
    void whenCreditCalledExistAccountAndValidTransaction_ItShouldReturnTransactionStatus() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Transaction transaction = generateTransaction("DepositTransaction");

        TransactionStatus transactionStatus = accountService.credit(result.getAccountNumber(), transaction);

        assertEquals("OK", transactionStatus.getStatus());

    }

    @Test
    void whenCreditCalledNonExistAccountAndValidTransaction_ItShouldThrowAccountNotFoundException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        Transaction transaction = generateTransaction("DepositTransaction");

        assertThrows(AccountNotFoundException.class,
                () -> accountService.credit("131313", transaction));

    }

    @Test
    void whenCreditCalledExistAccountAndNonValidTransaction_ItShouldThrowTransactionFailedException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Transaction transaction = generateTransaction("DepositTransaction");

        transaction.setAmount(-100);

        assertThrows(TransactionFailedException.class,
                () -> accountService.credit(result.getAccountNumber(), transaction));
    }

    @Test
    void whenDebitCalledExistAccountAndValidTransaction_ItShouldReturnTransactionStatus() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        createAccountRequest.setBalance(500);

        AccountDto result = accountService.createAccount(createAccountRequest);

        Transaction transaction = generateTransaction("WithdrawalTransaction");

        TransactionStatus transactionStatus = accountService.debit(result.getAccountNumber(), transaction);

        assertEquals("OK", transactionStatus.getStatus());
    }

    @Test
    void whenDebitCalledNonExistAccountAndValidTransaction_ItShouldThrowAccountNotFoundException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        Transaction transaction = generateTransaction("WithdrawalTransaction");

        assertThrows(AccountNotFoundException.class,
                () -> accountService.debit("131313", transaction));
    }

    @Test
    void whenDebitCalledExistAccountAndNonValidTransaction_ItShouldThrowInsufficientBalanceException() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Transaction transaction = generateTransaction("WithdrawalTransaction");

        transaction.setAmount(600);

        assertThrows(InsufficientBalanceException.class,
                () -> accountService.debit(result.getAccountNumber(), transaction));
    }

    @Test
    void whenUpdateAccountCalledWithValidRequest_ItShouldReturnAccountDto() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        UpdateAccountRequest request = new UpdateAccountRequest();

        request.setBalance(500);

        request.setAccountNumber(result.getAccountNumber());

        AccountDto updatedResult = accountService.updateAccount(result.getAccountNumber(), request);


        assertAll("request",
                () -> assertNotNull(updatedResult, updatedResult.getOwner()),
                () -> assertEquals("17892", result.getAccountNumber()),
                () -> assertEquals("17892", updatedResult.getAccountNumber()),
                () -> assertNotEquals(100, result.getBalance()),
                () -> assertEquals(500, updatedResult.getBalance())
        );
    }

    @Test
    void whenUpdateAccountCalledWithNonExistAccount_ItShouldReturnAccountDto() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        UpdateAccountRequest request = new UpdateAccountRequest();

        request.setAccountNumber("121212");

        assertThrows(AccountNotFoundException.class,
                () -> accountService.updateAccount(request.getAccountNumber(), request));
    }


    @Test
    void whenDeleteAccountCalledWithValidRequest_ItShouldDeleteAccount() {

        CreateAccountRequest createAccountRequest = generateAccountRequest();

        AccountDto result = accountService.createAccount(createAccountRequest);

        accountService.deleteAccount(result.getAccountNumber());

        Account account = accountRepository.findByAccountNumber(result.getAccountNumber()).orElse(null);

        assertNull(account);
    }

    // GENERATE DATA
    public CreateAccountRequest generateAccountRequest() {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();

        createAccountRequest.setAccountNumber("17892");

        createAccountRequest.setOwner("Kerem Karaca");

        return createAccountRequest;
    }

    public CreateAccountRequest generateAccountWithParam(String accountNumber, String owner) {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(accountNumber, owner);

        return createAccountRequest;
    }

    public Transaction generateTransaction(String transactionType) {

        if (transactionType == null) {
            return null;
        }

        if (transactionType.equalsIgnoreCase("DepositTransaction")) {

            DepositTransaction depositTransaction = new DepositTransaction();

            depositTransaction.setAmount(100);

            return depositTransaction;

        } else if (transactionType.equalsIgnoreCase("WithdrawalTransaction")) {

            WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();

            withdrawalTransaction.setType("WithdrawalTransaction");

            withdrawalTransaction.setAmount(100);

            return withdrawalTransaction;
        }
        return null;

    }
}