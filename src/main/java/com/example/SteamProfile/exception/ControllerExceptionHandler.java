package com.example.SteamProfile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {
        // Обработка ошибки 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка 400: " + ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        // Обработка ошибки 405
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Ошибка 405: Метод не разрешен");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Обработка ошибки 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка 500: " + ex.getMessage());
    }
}
