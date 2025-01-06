package com.myxxts.mls.server.model;

import lombok.Data;

@Data
public class HttpResponse<T> {

  private int code;

  private String message;

  private T data;

  public static <T> HttpResponse<T> success (T data) {

    HttpResponse<T> response = new HttpResponse<>();
    response.setCode(200);
    response.setMessage("success");
    response.setData(data);
    return response;

  }

  public static <T> HttpResponse<T> error (int code, String message) {

    HttpResponse<T> response = new HttpResponse<>();
    response.setCode(code);
    response.setMessage(message);
    return response;

  }

}
