package com.damien.bank.information.advice;

import com.damien.bank.information.exceptions.BankInformationServiceException;
import com.damien.bank.information.exceptions.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankInformationServiceControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserException(BankInformationServiceException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrorCode().getErrorCode(), ex.getErrorCode().getDescription());
        return new ResponseEntity<>(exceptionResponse, ex.getErrorCode().getHttpStatus());
    }
}
