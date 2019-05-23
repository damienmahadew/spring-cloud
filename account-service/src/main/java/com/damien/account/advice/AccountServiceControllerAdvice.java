package com.damien.account.advice;

import com.damien.account.exceptions.AccountServiceException;
import com.damien.account.exceptions.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountServiceControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserException(AccountServiceException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrorCode().getErrorCode(), ex.getErrorCode().getDescription());
        return new ResponseEntity<>(exceptionResponse, ex.getErrorCode().getHttpStatus());
    }
}
