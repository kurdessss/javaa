package com.example.SteamProfile.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.SteamProfile.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Before executing method: {}", methodName);
    }

    @AfterThrowing(pointcut = "execution(* com.example.SteamProfile.services.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception thrown in method: {}", methodName, ex);
    }
}
