package com.damien.bank.information.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BankInformationServiceException extends RuntimeException {

    private String errorDescription;
    private BankInformationServiceExceptionErrorCodes errorCode;

    public BankInformationServiceException(BankInformationServiceExceptionErrorCodes errorCode) {
        super(errorCode.toString());
        this.errorCode = BankInformationServiceExceptionErrorCodes.getErrorCodeFromValue(errorCode);;
    }

    public BankInformationServiceException(BankInformationServiceExceptionErrorCodes errorCode, Throwable exception) {
        super(errorCode.toString(), exception);
        this.errorCode = BankInformationServiceExceptionErrorCodes.getErrorCodeFromValue(errorCode);;
    }
}
