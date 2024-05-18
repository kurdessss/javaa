package com.example.SteamProfile.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler exceptionHandler = new ControllerExceptionHandler();

    @Test
    void handleHttpClientErrorException_ShouldReturnBadRequest() {
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");
        ResponseEntity<String> response = exceptionHandler.handleHttpClientErrorException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ошибка 400: 400 Bad Request", response.getBody()); // изменено на "Ошибка 400: 400 Bad Request"
    }

    @Test
    void handleMethodNotAllowedException_ShouldReturnMethodNotAllowed() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("Method Not Allowed");
        ResponseEntity<String> response = exceptionHandler.handleMethodNotAllowedException(ex);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("Ошибка 405: Метод не разрешен", response.getBody());
    }

    @Test
    void handleRuntimeException_ShouldReturnInternalServerError() {
        RuntimeException ex = new RuntimeException("Internal Server Error");
        ResponseEntity<String> response = exceptionHandler.handleRuntimeException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ошибка 500: Internal Server Error", response.getBody());
    }
}
