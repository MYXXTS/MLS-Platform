package com.myxxts.mls.server.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.myxxts.mls.server.security.entity.MLSAuthenticationDto;

public interface UserService extends UserDetailsService {

  void createUser (MLSAuthenticationDto mlsAuthenticationDto);

}
