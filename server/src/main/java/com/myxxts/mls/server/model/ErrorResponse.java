package com.myxxts.mls.server.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse of(int code, String message) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
