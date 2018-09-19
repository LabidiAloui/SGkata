package com.bank.kata.repository;

import com.bank.kata.model.Account;
import com.bank.kata.model.Client;
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
public class AccountRepositoryTest {

    public static final String ACCOUNT_NUMBER = "AZERTY";
    public static final String FIRST_NAME = "labidi";
    public static final String LAST_NAME = "ALOUI";
    public static final String BANK_ACCOUNT_NAME = "SG";
    public static final String ADDRESS = "1 rue maurice berteaux";
    private Client client;
    private RIB clientRib;
    private Account account;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

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

    }

    @Test
    public void should_get_account_by_client() {
        List<Account> accountList = accountRepository.findByClient(client);
        List<Account> myAccountList = new ArrayList<>();
        myAccountList.add(account);

        assertThat(accountList).isEqualTo(myAccountList);
    }

    @Test
    public void getOne() {
        Account returnedAccount = accountRepository.getOne(1l);
        assertThat(returnedAccount).isEqualTo(account);
    }

    @Test
    public void findByRibAccountNumber() {
        Account returnedAccount = accountRepository.findByRibAccountNumber(ACCOUNT_NUMBER);
        assertThat(returnedAccount).isEqualTo(account);
    }
}