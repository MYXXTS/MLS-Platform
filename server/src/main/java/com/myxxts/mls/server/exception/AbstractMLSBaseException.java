package com.myxxts.mls.server.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractMLSBaseException extends RuntimeException {

  public AbstractMLSBaseException (String message) {

    super(message);

  }

  public abstract HttpStatus getHttpStatus ();

}
