package com.damien.bank.information.services.impl;

import com.damien.bank.information.advice.InputNullCheckAdvice;
import com.damien.bank.information.clients.AccountClient;
import com.damien.bank.information.domain.account.Account;
import com.damien.bank.information.domain.bank.information.BankInformation;
import com.damien.bank.information.exceptions.BankInformationServiceException;
import com.damien.bank.information.exceptions.BankInformationServiceExceptionErrorCodes;
import com.damien.bank.information.services.BankInformationService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BankInformationServiceImplTest.BankInformationServiceImplTestConfig.class)
public class BankInformationServiceImplTest {

    private static final Long TEST_ACCOUNT_ID = 987654L;
    private static final String TEST_FIRST_NAME = "chuck";
    private static final String TEST_LAST_NAME = "norris";

    @Autowired
    private BankInformationService bankInformationService;

    @Autowired
    private AccountClient accountClient;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_getBankInformation_success() {
        when(accountClient.findByAccountId(TEST_ACCOUNT_ID)).thenReturn(new Account(TEST_ACCOUNT_ID, TEST_FIRST_NAME, TEST_LAST_NAME));

        BankInformation bankInformation = bankInformationService.getBankInformation(TEST_ACCOUNT_ID);

        Assert.assertNotNull(bankInformation);
        Assert.assertEquals(TEST_ACCOUNT_ID, bankInformation.getId());
        Assert.assertEquals(TEST_FIRST_NAME, bankInformation.getFirstName());
        Assert.assertEquals(TEST_LAST_NAME, bankInformation.getLastName());
        Assert.assertEquals("DE89370400440532987654", bankInformation.getIban());
    }

    @Configuration
    protected static class BankInformationServiceImplTestConfig {

        @Bean
        public BankInformationService bankInformationService() {
            return new BankInformationServiceImpl();
        }

        @Bean
        public AccountClient accountsRepository() {
            return Mockito.mock(AccountClient.class);
        }

        @Bean
        public InputNullCheckAdvice inputNullCheckAdvice() {
            return new InputNullCheckAdvice();
        }
    }
}