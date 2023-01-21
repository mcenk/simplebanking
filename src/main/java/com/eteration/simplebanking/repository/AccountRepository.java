package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


   Optional<Account> findByAccountNumber(String accountNumber);


}
