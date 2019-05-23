package com.damien.bank.information.controllers;

import com.damien.bank.information.clients.AccountClient;
import com.damien.bank.information.domain.account.Account;
import com.damien.bank.information.domain.bank.information.BankInformation;
import com.damien.bank.information.utilities.JsonUtility;
import org.junit.Assert;
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

import static com.damien.bank.information.controllers.BankInformationController.BANK_INFORMATION_GET_MAPPING;
import static com.damien.bank.information.controllers.BankInformationController.BASE_ENDPOINT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BankInformationControllerTestConfig.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class BankInformationControllerTest {

    private static final Long TEST_ACCOUNT_ID = 1L;
    private static final String TEST_LAST_NAME = "lee";
    private static final String TEST_FIRST_NAME = "bruce";

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private JsonUtility jsonUtility;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Ignore //TODO : add spring security test and setup spring security environment
    public void test_getBankAccountInformation_success() throws Exception {
        when(accountClient.findByAccountId(TEST_ACCOUNT_ID)).thenReturn(new Account(TEST_ACCOUNT_ID, TEST_FIRST_NAME, TEST_LAST_NAME));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(BASE_ENDPOINT + BANK_INFORMATION_GET_MAPPING);
        UriComponents components = uriComponentsBuilder.buildAndExpand(TEST_ACCOUNT_ID);

        String response = mockMvc.perform(get(components.toUriString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BankInformation bankInformation = jsonUtility.convertStringToObject(response, BankInformation.class);

        Assert.assertNotNull(bankInformation);
        Assert.assertEquals(TEST_ACCOUNT_ID, bankInformation.getId());
        Assert.assertEquals(TEST_FIRST_NAME, bankInformation.getFirstName());
        Assert.assertEquals(TEST_LAST_NAME, bankInformation.getLastName());
        Assert.assertEquals("DE89370400440532013081", bankInformation.getIban());
    }
}