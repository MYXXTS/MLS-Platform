package com.myxxts.mls.server.model.consts;

import org.springframework.http.HttpHeaders;

public class MLSConst {

  public static final class PathPrefix {

    public static final String API = "/api";

    public static final String COMMON = "/common";

    public static final String AUTH = "/auth";

    public static final String WEB = "/web";

    public static final String ADMIN = "/admin";

  }

  public static final class TokenType {

    public static final String WEB_ACCESS_TOKEN_NAME = "API-" + HttpHeaders.AUTHORIZATION;

    public static final String WEB_REFRESH_TOKEN_NAME = "API-" + HttpHeaders.AUTHORIZATION;

    public static final String ADMIN_ACCESS_TOKEN_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;

    public static final String ADMIN_REFRESH_TOKEN_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;

  }

}
