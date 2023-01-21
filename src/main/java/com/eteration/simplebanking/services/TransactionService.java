package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
