package com.bank.kata.repository;

import com.bank.kata.model.Account;
import com.bank.kata.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByAccount(Account account);
}
