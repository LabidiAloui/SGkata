package com.bank.kata.controller;

import com.bank.kata.enumeration.OperationType;
import com.bank.kata.model.Account;
import com.bank.kata.model.Client;
import com.bank.kata.model.Operation;
import com.bank.kata.model.RIB;
import com.bank.kata.service.AccountService;
import com.bank.kata.service.OperationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AccountControllerTest {

    public static final String ACCOUNT_NUMBER = "AZERTY";
    public static final String FIRST_NAME = "labidi";
    public static final String LAST_NAME = "ALOUI";
    public static final String BANK_ACCOUNT_NAME = "SG";
    public static final String ADDRESS = "1 rue maurice berteaux";
    private Client client;
    private RIB clientRib;
    private Account account;
    private Operation depositOperation;
    private Operation withdrawalOperation;
    private List<Operation> operationList;

    @InjectMocks
    AccountController accountController;
    @Mock
    private OperationService operationService;

    @Mock
    private AccountService accountService;

    @Before
    public void init() {

        //client
        client = new Client();
        client.setIdentifiant(1l);
        client.setFirstName(FIRST_NAME);
        client.setLastName(LAST_NAME);
        client.setAddress(ADDRESS);

        //rib

        clientRib = new RIB();
        clientRib.setAccountNumber(ACCOUNT_NUMBER);
        clientRib.setBankAccountName(BANK_ACCOUNT_NAME);
        clientRib.setCode(123l);

        account = new Account();
        account.setRib(clientRib);
        account.setClient(client);
        account.setAmount(200.75d);
        account.setOverDraft(200d);

        depositOperation = new Operation();
        depositOperation.setAmount(25d);
        depositOperation.setOperationType(OperationType.DEPOSIT);

        withdrawalOperation = new Operation();
        withdrawalOperation.setAmount(555d);
        withdrawalOperation.setOperationType(OperationType.WITHDRAWAL);

        operationList = new ArrayList<>();
        operationList.add(depositOperation);
        operationList.add(withdrawalOperation);
    }

    @Test
    public void should_return_operationList_for_account() {
        when(operationService.getOperationByAccountNumber(eq(ACCOUNT_NUMBER))).thenReturn(operationList);

        assertThat(accountController.getOperationListForAccount(ACCOUNT_NUMBER)).isEqualTo(operationList);

    }

    @Test
    public void depositOperation() {
        when(accountService.depositOperation(eq(ACCOUNT_NUMBER), eq(25d))).thenReturn(account);
        assertThat(accountController.depositOperation(ACCOUNT_NUMBER, 25d)).isEqualTo(account);
    }

    @Test
    public void withDrawalOperation() {
        when(accountService.withDrawalOperation(eq(ACCOUNT_NUMBER), eq(555d))).thenReturn(account);
        assertThat(accountController.withDrawalOperation(ACCOUNT_NUMBER, 555d)).isEqualTo(account);
    }
}