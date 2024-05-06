package com.example.SteamProfile.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import org.slf4j.Logger;



@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.SteamProfile.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before executing method: {}");
    }

    @AfterThrowing(pointcut = "execution(* com.example.SteamProfile.services.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error(joinPoint.getSignature().getName(), ex, Level.parse("Exception thrown in method: {}"));
    }
}
