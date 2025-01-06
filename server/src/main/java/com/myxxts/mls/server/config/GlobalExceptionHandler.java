package com.myxxts.mls.server.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myxxts.mls.server.exception.SystemNotInitializedException;
import com.myxxts.mls.server.model.HttpResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemNotInitializedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public HttpResponse<?> handleSystemNotInitialized(SystemNotInitializedException e) {
        return HttpResponse.error(503, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse<?> handleGlobalException(Exception e) {
        return HttpResponse.error(500, e.getMessage());
    }
}
