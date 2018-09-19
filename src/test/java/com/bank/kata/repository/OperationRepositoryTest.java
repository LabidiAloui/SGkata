package com.bank.kata.repository;

import com.bank.kata.enumeration.OperationType;
import com.bank.kata.model.Account;
import com.bank.kata.model.Client;
import com.bank.kata.model.Operation;
import com.bank.kata.model.RIB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OperationRepositoryTest {

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

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OperationRepository operationRepository;


    @Before
    public void init() {

        //client
        client = new Client();
        client.setIdentifiant(1l);
        client.setFirstName(FIRST_NAME);
        client.setLastName(LAST_NAME);
        client.setAddress(ADDRESS);
        clientRepository.save(client);
        //rib
        clientRib = new RIB();
        clientRib.setIdentifiant(1l);
        clientRib.setAccountNumber(ACCOUNT_NUMBER);
        clientRib.setBankAccountName(BANK_ACCOUNT_NAME);
        clientRib.setCode(123l);

        account = new Account();
        account.setIdentifiant(1l);
        account.setRib(clientRib);
        account.setClient(client);
        account.setAmount(200.75d);
        account.setOverDraft(200d);

        accountRepository.save(account);

        depositOperation = new Operation();
        depositOperation.setIdentifiant(1l);
        depositOperation.setAmount(25d);
        depositOperation.setOperationType(OperationType.DEPOSIT);
        depositOperation.setAccount(account);

        withdrawalOperation = new Operation();
        withdrawalOperation.setIdentifiant(2l);
        withdrawalOperation.setAmount(555d);
        withdrawalOperation.setOperationType(OperationType.WITHDRAWAL);
        withdrawalOperation.setAccount(account);


        operationList = new ArrayList<>();
        operationList.add(depositOperation);
        operationList.add(withdrawalOperation);

        operationRepository.saveAll(operationList);
    }

    @Test
    public void findByAccount() {

        List<Operation> persistedOperationsList = operationRepository.findByAccount(account);
        List<Operation> accountOperationsList = new ArrayList<>();
        accountOperationsList.addAll(operationList);

        assertThat(persistedOperationsList).isEqualTo(operationList);
    }
}