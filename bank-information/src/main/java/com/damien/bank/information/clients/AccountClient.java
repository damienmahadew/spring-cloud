package com.damien.bank.information.clients;

import com.damien.bank.information.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/accounts-service/secure/account/{id}")
    Account findByAccountId(@PathVariable("id") Long accountId);
}
