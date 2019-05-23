package com.damien.account.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountServiceException extends RuntimeException {

    private String errorDescription;
    private AccountServiceExceptionErrorCodes errorCode;

    public AccountServiceException(AccountServiceExceptionErrorCodes errorCode) {
        super(errorCode.toString());
        this.errorCode = AccountServiceExceptionErrorCodes.getErrorCodeFromValue(errorCode);;
    }

    public AccountServiceException(AccountServiceExceptionErrorCodes errorCode, Throwable exception) {
        super(errorCode.toString(), exception);
        this.errorCode = AccountServiceExceptionErrorCodes.getErrorCodeFromValue(errorCode);;
    }
}
