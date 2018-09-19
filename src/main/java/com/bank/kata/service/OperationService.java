package com.bank.kata.service;

import com.bank.kata.model.Account;
import com.bank.kata.model.Operation;
import com.bank.kata.repository.AccountRepository;
import com.bank.kata.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OperationService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationRepository operationRepository;

    public List<Operation> getOperationByAccountNumber(String accountNumber) {
        List<Operation> returnedList = new ArrayList<>();

        Account account = accountRepository.findByRibAccountNumber(accountNumber);
        if(Objects.nonNull(account)) {
            returnedList = operationRepository.findByAccount(account);
        }

        return returnedList;
    }
}
