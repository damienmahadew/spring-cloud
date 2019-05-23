package com.damien.account.controllers.accounts;

import com.damien.account.domain.accounts.CreateAccountRequest;
import com.damien.account.domain.entities.Account;
import com.damien.account.interfaces.LogHttpRequest;
import com.damien.account.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(AccountsController.BASE_ENDPOINT)
@LogHttpRequest
public class AccountsController {

    private static final String ID_IDENTIFIER = "id";

    public static final String BASE_ENDPOINT = "/api/accounts-service";
    public static final String ALL_ACCOUNTS_GET_MAPPING = "/accounts";
    public static final String CREATE_OR_GET_ACCOUNT_MAPPING = "/secure/account/{" + ID_IDENTIFIER + "}";

    @Autowired
    private AccountsService accountsService;

    @GetMapping(AccountsController.ALL_ACCOUNTS_GET_MAPPING)
    public List<Account> getAllAccounts() {
        return accountsService.getAllAccounts();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(AccountsController.CREATE_OR_GET_ACCOUNT_MAPPING)
    public void createAccount(@PathVariable(ID_IDENTIFIER) @NotNull Long id, @RequestBody @Valid CreateAccountRequest createAccountRequest) {
        accountsService.createAccount(id, createAccountRequest.getFirstName(), createAccountRequest.getLastName());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(AccountsController.CREATE_OR_GET_ACCOUNT_MAPPING)
    public Account getAccount(@PathVariable(ID_IDENTIFIER) @NotNull Long id) {
        return accountsService.getAccount(id);
    }
}
