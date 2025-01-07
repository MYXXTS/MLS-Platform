package com.myxxts.mls.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myxxts.mls.server.model.ErrorResponse;

public class GlobalExceptionHandler {

  @ExceptionHandler (value = Exception.class)
  public ErrorResponse exceptionHandler (Exception e) {

    return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

  }

}
