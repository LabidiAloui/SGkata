package com.bank.kata.service;

import com.bank.kata.enumeration.OperationType;
import com.bank.kata.model.Account;
import com.bank.kata.model.Operation;
import com.bank.kata.repository.AccountRepository;
import com.bank.kata.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationRepository operationRepository;

    @Transactional
    public Account depositOperation(String accountNumber, double amount) {
        Account account = checkAccountExistence(accountNumber);

        double oldAmount = account.getAmount();
        log.info("deposit operation for account : "+ accountNumber + "old amount = "+ oldAmount + "new amount :"+ amount);
        // add new amount
        double newAmount = oldAmount + amount;
        account.setAmount(newAmount);

        //save deposit withdrawal
        Operation operation = new Operation();
        operation.setAmount(newAmount);
        operation.setOperationType(OperationType.DEPOSIT);

        operationRepository.save(operation);
        return accountRepository.save(account);

    }

    @Transactional
    public Account withDrawalOperation(String accountNumber, double amount) {
        Account account = checkAccountExistence(accountNumber);

        double oldAmount = account.getAmount();
        log.info("withdrawal operation for account : "+ accountNumber + "old amount = "+ oldAmount + "new amount :"+ amount);

        checkAccountAmount(account, amount);

        // add new amount
        double newAmount = oldAmount - amount;
        account.setAmount(newAmount);

        //save operation withdrawal
        Operation operation = new Operation();
        operation.setAmount(newAmount);
        operation.setOperationType(OperationType.WITHDRAWAL);

        operationRepository.save(operation);

        return accountRepository.save(account);

    }

    private void checkAccountAmount(Account account, double amount) {
        if((account.getAmount() + account.getOverDraft() - amount) < 0) {
            throw new RuntimeException("This operation is not athowired !");
        }
    }

    private Account checkAccountExistence(String accountNumber) {
        Account account = accountRepository.findByRibAccountNumber(accountNumber);
        if(Objects.isNull(account)) {
            throw new RuntimeException("No account found with this number :" + accountNumber);
        }
        return account;
    }
}
