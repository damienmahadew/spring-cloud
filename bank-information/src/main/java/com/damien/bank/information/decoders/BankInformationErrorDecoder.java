package com.damien.bank.information.decoders;

import com.damien.bank.information.exceptions.BankInformationServiceException;
import com.damien.bank.information.exceptions.BankInformationServiceExceptionErrorCodes;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class BankInformationErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.ACCOUNT_DOES_NOT_EXIST);
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}