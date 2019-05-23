package com.damien.account.services.impl;

import com.damien.account.domain.entities.Account;
import com.damien.account.exceptions.AccountServiceException;
import com.damien.account.exceptions.AccountServiceExceptionErrorCodes;
import com.damien.account.interfaces.LogMethod;
import com.damien.account.interfaces.NullCheck;
import com.damien.account.repository.AccountsRepository;
import com.damien.account.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@LogMethod
@NullCheck
public class AccountsServiceImpl implements AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountsRepository.findAll();
    }

    @Override
    public void createAccount(Long id, String firstName, String lastName) {
        validateIfAccountExists(id);

        Account newAccount = new Account(id, firstName, lastName);

        accountsRepository.save(newAccount);
    }

    @Override
    public Account getAccount(Long id) {
        Optional<Account> accountOptional = accountsRepository.findById(id);

        return accountOptional.orElseThrow(() -> new AccountServiceException(AccountServiceExceptionErrorCodes.ACCOUNT_DOES_NOT_EXIST));
    }

    private void validateIfAccountExists(Long id) {
        Optional<Account> accountOptional = accountsRepository.findById(id);

        accountOptional.ifPresent((account) -> {
            throw new AccountServiceException(AccountServiceExceptionErrorCodes.ACCOUNT_EXISTS);
        });
    }
}
