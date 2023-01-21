//package com.eteration.simplebanking;
//
//import com.eteration.simplebanking.dto.AccountDtoConverter;
//import com.eteration.simplebanking.request.CreateAccountRequest;
//import com.eteration.simplebanking.repository.AccountRepository;
//import com.eteration.simplebanking.services.AccountService;
//import com.eteration.simplebanking.services.ApprovalCodeService;
//import com.eteration.simplebanking.services.TransactionService;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//public class CustomControllerTest {
//
//    private AccountService accountService;
//    private  AccountRepository accountRepository;
//    private  TransactionService transactionService;
//    private  ApprovalCodeService codeService;
//    private  AccountDtoConverter accountDtoConverter;
//
//
//
//    @Before
//    public void start() throws Exception{
//        accountRepository = Mockito.mock(AccountRepository.class);
//        transactionService = Mockito.mock(TransactionService.class);
//        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);
//        codeService=Mockito.mock(ApprovalCodeService.class);
//
//
//        accountService = new AccountService(accountRepository,
//                transactionService,codeService,
//                accountDtoConverter);
//    }
//    @Test
//    public void whenCreateAccountReturnValidResponse_TrowException() {
//
//        CreateAccountRequest createAccountRequest= generateCreateAccountRequest();
//
//
//        Mockito.when(accountService.createAccount(createAccountRequest)).thenReturn(a)
//
//
//    }
//
//    private CreateAccountRequest generateCreateAccountRequest(){
//        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234", "Eteration Name");
//        return createAccountRequest;
//    }
////
////    private Customer generateCustomer() {
////        return Customer.builder()
////                .id("12345")
////                .address(Address.builder().city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build())
////                .city(City.ISTANBUL)
////                .dateOfBirth(1998)
////                .name("Muratcan")
////                .build();
////    }
//
////    private Account generateAccount(CreateAccountRequest accountRequest) {
////        return Account.builder()
////                .id(accountRequest.getId())
////                .balance(accountRequest.getBalance())
////                .currency(accountRequest.getCurrency())
////                .customerId(accountRequest.getCustomerId())
////                .city(accountRequest.getCity())
////                .build();
////    }
////
////    private AccountDto generateAccountDto() {
////        return AccountDto.builder()
////                .id("1234")
////                .customerId("12345")
////                .currency(Currency.TRY)
////                .balance(100.0)
////                .build();
////    }
//
//
//
//
//
//
//}
//
//
//
//
//
//}
