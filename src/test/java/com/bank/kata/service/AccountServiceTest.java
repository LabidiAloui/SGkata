package com.bank.kata.service;

import com.bank.kata.enumeration.OperationType;
import com.bank.kata.model.Account;
import com.bank.kata.model.Client;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

    public static final String ACCOUNT_NUMBER = "AZERTY";
    public static final String FIRST_NAME = "labidi";
    public static final String LAST_NAME = "ALOUI";
    public static final String BANK_ACCOUNT_NAME = "SG";
    public static final String ADDRESS = "1 rue maurice berteaux";
    private Client client;
    private RIB clientRib;
    private Account account;
    private Account accountAfterDeposit;
    private Account accountAfterWithDrawal;
    private Operation depositOperation;
    private Operation withdrawalOperation;
    private List<Operation> operationList;

    @InjectMocks
    AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

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

        accountAfterDeposit = account;
        accountAfterDeposit.setAmount(account.getAmount() + 25d);

        accountAfterWithDrawal = account;
        accountAfterWithDrawal.setAmount(account.getAmount() - 55d);

        depositOperation = new Operation();
        depositOperation.setAmount(25d);
        depositOperation.setOperationType(OperationType.DEPOSIT);

        withdrawalOperation = new Operation();
        withdrawalOperation.setAmount(55d);
        withdrawalOperation.setOperationType(OperationType.WITHDRAWAL);

        operationList = new ArrayList<>();
        operationList.add(depositOperation);
        operationList.add(withdrawalOperation);
    }


    @Test
    public void should_do_deposit_operation() {
        doReturn(account).when(accountRepository).findByRibAccountNumber(ACCOUNT_NUMBER);
        doReturn(accountAfterDeposit).when(accountRepository).save(accountAfterDeposit);
        assertThat(accountService.depositOperation(ACCOUNT_NUMBER, 25d)).isEqualTo(accountAfterDeposit);
    }

    @Test
    public void should_do_withdrawal_operation() {
        doReturn(account).when(accountRepository).findByRibAccountNumber(ACCOUNT_NUMBER);
        doReturn(accountAfterWithDrawal).when(accountRepository).save(accountAfterWithDrawal);
        assertThat(accountService.withDrawalOperation(ACCOUNT_NUMBER, 55d)).isEqualTo(accountAfterWithDrawal);

    }

    @Test
    public void should_return_exception_when_account_is_overdraft_amount() {
        doReturn(account).when(accountRepository).findByRibAccountNumber(ACCOUNT_NUMBER);
        doReturn(accountAfterWithDrawal).when(accountRepository).save(accountAfterWithDrawal);
        assertThatThrownBy(() -> accountService.withDrawalOperation(ACCOUNT_NUMBER, 100000d))
                .isInstanceOf(RuntimeException.class);
    }
}