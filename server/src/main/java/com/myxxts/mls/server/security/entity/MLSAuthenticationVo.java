package com.myxxts.mls.server.security.entity;

import java.util.Set;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude (JsonInclude.Include.NON_NULL)
public class MLSAuthenticationVo {

  private String nickname;

  private String username;

  private String email;

  private String accessToken;

  private String refreshToken;

  private Set<ObjectId> rids;

}
