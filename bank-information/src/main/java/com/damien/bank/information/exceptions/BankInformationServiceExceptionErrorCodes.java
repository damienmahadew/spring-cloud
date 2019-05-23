package com.damien.bank.information.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum BankInformationServiceExceptionErrorCodes {

    ACCOUNT_EXISTS("BIS0001", "Account exists", HttpStatus.PRECONDITION_FAILED),
    ACCOUNT_DOES_NOT_EXIST("BIS0002", "Account does not exist", HttpStatus.NOT_FOUND),
    NULL_INPUT("BISS0003", "Null Inputs", HttpStatus.INTERNAL_SERVER_ERROR),
    JSON_EXCEPTION("BIS0004", "Unable to parse Json", HttpStatus.INTERNAL_SERVER_ERROR),
    IO_EXCEPTION("BIS0005", "IO Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    DEFAULT("BISS9999", "Default error message", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorCode;
    private String description;
    private HttpStatus httpStatus;

    public static BankInformationServiceExceptionErrorCodes getErrorCodeFromValue(BankInformationServiceExceptionErrorCodes userProfileErrorCodes) {
        if  (userProfileErrorCodes == null) {
            return BankInformationServiceExceptionErrorCodes.DEFAULT;
        }

        return userProfileErrorCodes;
    }
}
