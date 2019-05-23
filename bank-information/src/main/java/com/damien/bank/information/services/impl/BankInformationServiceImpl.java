package com.damien.bank.information.services.impl;

import com.damien.bank.information.clients.AccountClient;
import com.damien.bank.information.domain.account.Account;
import com.damien.bank.information.domain.bank.information.BankInformation;
import com.damien.bank.information.services.BankInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BankInformationServiceImpl implements BankInformationService {

    private static final String BASE_IBAN = "DE89370400440532013087";

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BankInformation getBankInformation(Long accountId) {
//        Account account = accountClient.findByAccountId(accountId);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://ACCOUNT-SERVICE/api/accounts-service/secure/account/{id}")
                .buildAndExpand(accountId);
        Account account = restTemplate.getForObject(uriComponents.toUriString(), Account.class);

        String iban = getIban(accountId);

        return buildBankInformation(account, iban);
    }

    private BankInformation buildBankInformation(Account account, String iban) {
        return BankInformation.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .iban(iban)
                .build();
    }

    private String getIban(Long accountId) {
        String accountIdAsString = String.valueOf(accountId);
        int endIndex = BASE_IBAN.length() - accountIdAsString.length();
        return BASE_IBAN.substring(0, endIndex) + accountIdAsString;
    }
}
