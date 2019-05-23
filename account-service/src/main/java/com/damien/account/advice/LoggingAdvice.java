package com.damien.account.advice;

import com.damien.account.utilities.JsonUtility;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Aspect
@Configuration
public class LoggingAdvice {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BODY_IDENTIFIER = "body";
    private static final String REQUEST_URI_IDENTIFIER = "requestUri";
    private static final String REMOTE_HOST_IDENTIFIER = "remoteHost";
    private static final Set<String> HEADERS_TO_LOG = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(AUTHORIZATION_HEADER)));

    @Autowired
    private JsonUtility jsonUtility;

    @Around("@within(com.damien.account.interfaces.LogMethod) || @annotation(com.damien.account.interfaces.LogMethod)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = getLogger(joinPoint);

        String methodName = joinPoint.getSignature().getName();

        logMethodDetails(joinPoint.getArgs(), methodName, logger);

        return timeAndExecute(joinPoint, logger, methodName);
    }

    private void logMethodDetails(Object[] args, String methodName, Logger logger) {
        String log = jsonUtility.convertObjectToString(args);

        logger.info("Executing method [{}] with request [{}]", methodName, log);
    }

    private Logger getLogger(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        return LoggerFactory.getLogger(className);
    }

    @Around("@within(com.damien.account.interfaces.LogHttpRequest) || @annotation(com.damien.account.interfaces.LogHttpRequest)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = getLogger(joinPoint);

        String methodName = joinPoint.getSignature().getName();

        logHttpRequest(methodName, logger);

        return timeAndExecute(joinPoint, logger, methodName);
    }

    private Object timeAndExecute(ProceedingJoinPoint joinPoint, Logger logger, String methodName) throws Throwable {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            Object result = joinPoint.proceed();

            stopWatch.stop();

            logger.info("Method [{}] executed within [{}] miliseconds with response [{}]", methodName, stopWatch.getTotalTimeMillis(), jsonUtility.convertObjectToString(result));

            return result;
        } catch (Exception ex) {
            logger.error("Exception was raised while trying to execute method " + methodName, ex);
            throw ex;
        }
    }

    private void logHttpRequest(String methodName, Logger logger) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String> loggingObjects = buildMapOfObjectsToLogFromHttpRequest(request);

        MDC.put(AUTHORIZATION_HEADER, loggingObjects.get(AUTHORIZATION_HEADER));

        String log = jsonUtility.convertObjectToString(loggingObjects);

        logger.info("Executing method [{}] with request [{}]", methodName, log);
    }

    private Map<String, String> buildMapOfObjectsToLogFromHttpRequest(HttpServletRequest request) throws IOException {
        Map<String, String> loggingObjects = new HashMap<>();

        HEADERS_TO_LOG.stream()
                .filter(header -> request.getHeader(header) != null)
                .forEach(header -> loggingObjects.put(header, request.getHeader(header)));

        loggingObjects.put(REQUEST_URI_IDENTIFIER, request.getRequestURI());
        loggingObjects.put(REMOTE_HOST_IDENTIFIER, request.getRemoteHost());
        return loggingObjects;
    }
}
