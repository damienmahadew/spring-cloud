package com.damien.account.services.impl;

import com.damien.account.advice.InputNullCheckAdvice;
import com.damien.account.domain.entities.Account;
import com.damien.account.exceptions.AccountServiceException;
import com.damien.account.exceptions.AccountServiceExceptionErrorCodes;
import com.damien.account.repository.AccountsRepository;
import com.damien.account.services.AccountsService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AccountsServiceImplTest.AccountsServiceImplTestConfig.class)
public class AccountsServiceImplTest {

    private static final Long TEST_ID = 1L;
    private static final String TEST_FIRST_NAME = "bruce";
    private static final String TEST_LAST_NAME = "willis";

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsService accountsService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_getAllAccounts_success() {
        List<Account> accountsList = accountsService.getAllAccounts();

        verify(accountsRepository, times(1)).findAll();
    }

    /**
     * Given:
     *  Account ID = 1 does not exist
     * When:
     *  createAccount (id, firstName, lastName)
     * Then:
     *  create account successfully
     */
    @Test
    public void test_createAccount_success() {
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);

        when(accountsRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        accountsService.createAccount(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME);

        verify(accountsRepository, times(1)).save(accountArgumentCaptor.capture());

        Account value = accountArgumentCaptor.getValue();
        Assert.assertNotNull(value);
        Assert.assertEquals(TEST_ID, value.getId());
        Assert.assertEquals(TEST_FIRST_NAME, value.getFirstName());
        Assert.assertEquals(TEST_LAST_NAME, value.getLastName());
    }

    /**
     * Given:
     *  Account ID = 1 does exist
     * When:
     *  createAccount (id, firstName, lastName)
     * Then:
     *  expect error pre condition failed
     */
    @Test
    public void test_createAccount_accountExists() {
        expectedException.expect(AccountServiceException.class);
        expectedException.expectMessage(AccountServiceExceptionErrorCodes.ACCOUNT_EXISTS.getDescription());

        when(accountsRepository.findById(TEST_ID)).thenReturn(Optional.of(new Account()));

        accountsService.createAccount(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    /**
     * Given:
     *  User ID = 1 does exist
     * When:
     *  getAccount(1) is called
     * Then:
     *  expect account to be returned
     */
    @Test
    public void test_getAccount_success() {
        when(accountsRepository.findById(TEST_ID)).thenReturn(Optional.of(new Account(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME)));

        Account account = accountsService.getAccount(TEST_ID);

        verify(accountsRepository, times(1)).findById(eq(TEST_ID));

        Assert.assertEquals(TEST_ID, account.getId());
        Assert.assertEquals(TEST_FIRST_NAME, account.getFirstName());
        Assert.assertEquals(TEST_LAST_NAME, account.getLastName());
    }

    /**
     * Given:
     *  User ID = 1 does not exist
     * When:
     *  getAccount(1) is called
     * Then:
     *  expect exception of Account does not exist
     */
    @Test
    public void test_getAccount_userDoesNotExist() {
        expectedException.expect(AccountServiceException.class);
        expectedException.expectMessage(AccountServiceExceptionErrorCodes.ACCOUNT_DOES_NOT_EXIST.getDescription());

        when(accountsRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        accountsService.getAccount(TEST_ID);
    }

    @Configuration
    protected static class AccountsServiceImplTestConfig {

        @Bean
        public AccountsService accountsService() {
            return new AccountsServiceImpl();
        }

        @Bean
        public AccountsRepository accountsRepository() {
            return Mockito.mock(AccountsRepository.class);
        }

        @Bean
        public InputNullCheckAdvice inputNullCheckAdvice() {
            return new InputNullCheckAdvice();
        }
    }
}