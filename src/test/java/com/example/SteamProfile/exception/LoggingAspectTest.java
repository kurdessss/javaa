package com.example.SteamProfile.exception;

import com.example.SteamProfile.exception.LoggingAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @MockBean
    private Logger logger;

    @Autowired
    private LoggingAspect loggingAspect;

    @Mock
    private JoinPoint joinPoint;

    @Test
    public void testLogAfterThrowing() {
        Signature signature = mock(Signature.class);
        when(signature.getName()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);

        Throwable throwable = new RuntimeException("Test exception");
        loggingAspect.logAfterThrowing(joinPoint, throwable);

        verify(logger).error("Exception thrown in method: {}", "testMethod", throwable);
    }

    @Test
    public void testLogBefore() {
        Signature signature = mock(Signature.class);
        when(signature.getName()).thenReturn("testMethod");
        when(joinPoint.getSignature()).thenReturn(signature);

        loggingAspect.logBefore(joinPoint);

        verify(logger).info("Before executing method: {}", "testMethod");
    }

}
