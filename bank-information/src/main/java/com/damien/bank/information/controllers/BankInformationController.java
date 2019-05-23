package com.damien.bank.information.controllers;

import com.damien.bank.information.domain.bank.information.BankInformation;
import com.damien.bank.information.services.BankInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(BankInformationController.BASE_ENDPOINT)
public class BankInformationController {

    private static final String ID_IDENTIFIER = "id";

    public static final String BASE_ENDPOINT = "/api/bank-information";
    public static final String BANK_INFORMATION_GET_MAPPING = "/bank/{" + ID_IDENTIFIER + "}";

    @Autowired
    private BankInformationService bankInformationService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(BANK_INFORMATION_GET_MAPPING)
    public BankInformation getBankAccountInformation(@PathVariable(ID_IDENTIFIER) @Valid @NotNull Long accountId) {
        return bankInformationService.getBankInformation(accountId);
    }
}
