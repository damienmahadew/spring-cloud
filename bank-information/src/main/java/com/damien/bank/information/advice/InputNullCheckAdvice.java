package com.damien.bank.information.advice;

import com.damien.bank.information.exceptions.BankInformationServiceException;
import com.damien.bank.information.exceptions.BankInformationServiceExceptionErrorCodes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Configuration
public class InputNullCheckAdvice {

    @Before("execution(* com.damien.bank.information.services.*.*(..))")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        Boolean hasNullInput = Arrays.asList(args).stream().anyMatch(o -> Objects.isNull(o));

        if (hasNullInput) {
            throw new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.NULL_INPUT);
        }
    }
}
