package com.damien.account.advice;

import com.damien.account.exceptions.AccountServiceException;
import com.damien.account.exceptions.AccountServiceExceptionErrorCodes;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

@Aspect
@Configuration
public class InputNullCheckAdvice {

    @Before("@within(com.damien.account.interfaces.NullCheck) || @annotation(com.damien.account.interfaces.NullCheck)")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Boolean hasNullInput = Arrays.asList(args).stream().anyMatch(o -> Objects.isNull(o));

        if (hasNullInput) {
            throw new AccountServiceException(AccountServiceExceptionErrorCodes.NULL_INPUT);
        }
    }
}
