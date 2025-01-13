package com.myxxts.mls.server.exception;

import org.springframework.http.HttpStatus;

public class SystemNotInitializedException extends AbstractMLSBaseException {

  public SystemNotInitializedException (String message) {

    super(message);

  }

  @Override
  public HttpStatus getHttpStatus () {

    return HttpStatus.SERVICE_UNAVAILABLE;

  }

}
