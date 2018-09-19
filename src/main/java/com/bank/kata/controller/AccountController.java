package com.bank.kata.controller;


import com.bank.kata.model.Account;
import com.bank.kata.model.Operation;
import com.bank.kata.service.AccountService;
import com.bank.kata.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    OperationService operationService;

    @GetMapping(value="/{accountNumber}")
    public List<Operation> getOperationListForAccount(@PathVariable("accountNumber")String accountNumber){
        return operationService.getOperationByAccountNumber(accountNumber);
    }

    @PutMapping(value = "/deposit/{accountNumber}")
    public Account depositOperation(@PathVariable String accountNumber,
                                    @RequestBody double amount) {
        return accountService.depositOperation(accountNumber, amount);
    }

    @PutMapping(value = "/withdrawal/{accountNumber}")
    public Account withDrawalOperation(@PathVariable String accountNumber,
                                    @RequestBody double amount) {
        return accountService.withDrawalOperation(accountNumber, amount);
    }
}
