package com.damien.account.controllers.accounts;

import com.damien.account.AccountServiceApplication;
import com.damien.account.domain.accounts.CreateAccountRequest;
import com.damien.account.domain.entities.Account;
import com.damien.account.exceptions.AccountServiceExceptionErrorCodes;
import com.damien.account.exceptions.ExceptionResponse;
import com.damien.account.repository.AccountsRepository;
import com.damien.account.utilities.JsonUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static com.damien.account.controllers.accounts.AccountsController.ALL_ACCOUNTS_GET_MAPPING;
import static com.damien.account.controllers.accounts.AccountsController.BASE_ENDPOINT;
import static com.damien.account.controllers.accounts.AccountsController.CREATE_OR_GET_ACCOUNT_MAPPING;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AccountServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class AccountsControllerTest {

    public static final String TEST_FIRST_NAME = "Black";
    public static final String TEST_LAST_NAME = "Panther";

    @Autowired
    private AccountsController accountsController;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtility jsonUtility;

    @Before
    public void before() {
        reset();
    }

    /**
     * Given:
     *  Datasource has 5 accounts
     * When:
     *  getAllAccounts is called
     * Then:
     *  return return status 200 and the 5 accounts
     */
    @Test
    public void test_getAllAccounts_success() throws Exception {
        String response = mockMvc.perform(get(BASE_ENDPOINT + ALL_ACCOUNTS_GET_MAPPING)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Account> accountList = jsonUtility.convertStringToObject(response, new TypeReference<List<Account>>() {
        });

        Assert.assertNotNull(accountList);
        Assert.assertEquals(5, accountList.size());
    }

    /**
     * Given:
     *  Account with ID 7 does not exist
     *  FirstName is populated
     *  LastName is populated
     * When:
     *  createAccount is called
     * Then:
     *  return return status 200 and data is persisted
     */
    @Test
    @Ignore //TODO: add spring security test and enable spring security test environment
    public void test_createAccount_success() throws Exception {
        Long id = 7L;
        String objectAsString = jsonUtility.convertObjectToString(new CreateAccountRequest(TEST_FIRST_NAME, TEST_LAST_NAME));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_ENDPOINT + CREATE_OR_GET_ACCOUNT_MAPPING);
        UriComponents components = uriComponentsBuilder.buildAndExpand(id);

        String response = mockMvc.perform(post(components.toUriString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectAsString))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Optional<Account> account = accountsRepository.findById(id);
        Assert.assertTrue(account.isPresent());
        Account actual = account.get();
        Assert.assertEquals(TEST_FIRST_NAME, actual.getFirstName());
        Assert.assertEquals(TEST_LAST_NAME, actual.getLastName());
    }

    /**
     * Given:
     *  Account with ID 1 does exist
     *  FirstName is populated
     *  LastName is populated
     * When:
     *  createAccount is called
     * Then:
     *  return return status 200 and data is persisted
     */
    @Test
    @Ignore
    public void test_createAccount_accountExists() throws Exception {
        String objectAsString = jsonUtility.convertObjectToString(new CreateAccountRequest(TEST_FIRST_NAME, TEST_LAST_NAME));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_ENDPOINT + CREATE_OR_GET_ACCOUNT_MAPPING);
        UriComponents components = uriComponentsBuilder.buildAndExpand("1");

        String response = mockMvc.perform(post(components.toUriString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectAsString))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertForExceptionResponse(response, AccountServiceExceptionErrorCodes.ACCOUNT_EXISTS);
    }

    /**
     * Given:
     *  Account with ID 1 does exist
     * When:
     *  getAccount is called
     * Then:
     *  return return status 200 and account data for ID = 1
     */
    @Test
    @Ignore
    public void test_getAccount_success() throws Exception {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_ENDPOINT + CREATE_OR_GET_ACCOUNT_MAPPING);
        UriComponents components = uriComponentsBuilder.buildAndExpand("1");

        String response = mockMvc.perform(get(components.toUriString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Account account = jsonUtility.convertStringToObject(response, Account.class);

        Assert.assertNotNull(account);
        Assert.assertEquals("Damien", account.getFirstName());
        Assert.assertEquals("Mahadew", account.getLastName());
    }

    /**
     * Given:
     *  Account with ID 1 does not exist
     * When:
     *  getAccount is called
     * Then:
     *  return return status 404
     */
    @Test
    @Ignore
    public void test_getAccount_userDoesNotExist() throws Exception {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_ENDPOINT + CREATE_OR_GET_ACCOUNT_MAPPING);
        UriComponents components = uriComponentsBuilder.buildAndExpand("100");

        String response = mockMvc.perform(get(components.toUriString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertForExceptionResponse(response, AccountServiceExceptionErrorCodes.ACCOUNT_DOES_NOT_EXIST);
    }

    private void assertForExceptionResponse(String response, AccountServiceExceptionErrorCodes errorCode) {
        ExceptionResponse exceptionResponse = jsonUtility.convertStringToObject(response, ExceptionResponse.class);
        Assert.assertNotNull(exceptionResponse);
        Assert.assertEquals(errorCode.getErrorCode(), exceptionResponse.getErrorCode());
        Assert.assertEquals(errorCode.getDescription(), exceptionResponse.getDetails());
    }
}