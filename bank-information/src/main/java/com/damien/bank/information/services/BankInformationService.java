package com.damien.bank.information.services;

import com.damien.bank.information.domain.bank.information.BankInformation;

public interface BankInformationService {

    BankInformation getBankInformation(Long accountId);
}
