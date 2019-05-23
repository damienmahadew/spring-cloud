package com.damien.account.services;

import com.damien.account.domain.entities.Account;

import java.util.List;

public interface AccountsService {

    List<Account> getAllAccounts();

    void createAccount(Long id, String firstName, String lastName);

    Account getAccount(Long id);
}
