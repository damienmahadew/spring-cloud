package com.damien.account.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum AccountServiceExceptionErrorCodes {

    ACCOUNT_EXISTS("AS0001", "Account exists", HttpStatus.PRECONDITION_FAILED),
    ACCOUNT_DOES_NOT_EXIST("AS0002", "Account does not exist", HttpStatus.NOT_FOUND),
    NULL_INPUT("AS0003", "Null Inputs", HttpStatus.INTERNAL_SERVER_ERROR),
    JSON_EXCEPTION("AS0004", "Unable to parse Json", HttpStatus.INTERNAL_SERVER_ERROR),
    IO_EXCEPTION("AS0005", "IO Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    DEFAULT("AS9999", "Default error message", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorCode;
    private String description;
    private HttpStatus httpStatus;

    public static AccountServiceExceptionErrorCodes getErrorCodeFromValue(AccountServiceExceptionErrorCodes userProfileErrorCodes) {
        if  (userProfileErrorCodes == null) {
            return AccountServiceExceptionErrorCodes.DEFAULT;
        }

        return userProfileErrorCodes;
    }
}
