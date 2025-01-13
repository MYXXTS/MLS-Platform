package com.myxxts.mls.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myxxts.mls.server.model.common.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (SystemNotInitializedException.class)
    @ResponseStatus (HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleSystemNotInitialized (SystemNotInitializedException e) {
        return ErrorResponse.of(503, e.getMessage());
    }

    @ExceptionHandler (Exception.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGlobalException (Exception e) {
        return ErrorResponse.of(500, e.getMessage());
    }
}
