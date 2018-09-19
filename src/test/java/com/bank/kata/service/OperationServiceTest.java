package com.bank.kata.service;

import com.bank.kata.enumeration.OperationType;
import com.bank.kata.model.Account;
import com.bank.kata.model.Operation;
import com.bank.kata.model.RIB;
import com.bank.kata.repository.AccountRepository;
import com.bank.kata.repository.OperationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class OperationServiceTest {

    public static final String ACCOUNT_NUMBER = "AZERTY";
    public static final String BANK_ACCOUNT_NAME = "SG";
    private Operation depositOperation;
    private Operation withdrawalOperation;
    private List<Operation> operationList;
    private Account account = new Account();
    private RIB clientRib;

    @InjectMocks
    OperationService operationService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountRepository accountRepository;


    @Before
    public void init() {

        clientRib = new RIB();
        clientRib.setAccountNumber(ACCOUNT_NUMBER);
        clientRib.setBankAccountName(BANK_ACCOUNT_NAME);
        clientRib.setCode(123l);
        account.setRib(clientRib);

        depositOperation = new Operation();
        depositOperation.setAmount(25d);
        depositOperation.setOperationType(OperationType.DEPOSIT);

        withdrawalOperation = new Operation();
        withdrawalOperation.setAmount(55d);
        withdrawalOperation.setOperationType(OperationType.WITHDRAWAL);

        operationList = new ArrayList<>();
        operationList.add(depositOperation);
        operationList.add(withdrawalOperation);

        account.setOperationList(operationList);
    }

    @Test
    public void getOperationByAccountNumber() {
        doReturn(account).when(accountRepository).findByRibAccountNumber(ACCOUNT_NUMBER);
        doReturn(operationList).when(operationRepository).findByAccount(account);
        assertThat(operationService.getOperationByAccountNumber(ACCOUNT_NUMBER)).hasSize(2).containsExactly(depositOperation, withdrawalOperation);
    }
}