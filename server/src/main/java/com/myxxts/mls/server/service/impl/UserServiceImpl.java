package com.myxxts.mls.server.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myxxts.mls.server.model.entity.User;
import com.myxxts.mls.server.repository.UserRepository;
import com.myxxts.mls.server.security.entity.MLSAuthenticationDto;
import com.myxxts.mls.server.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  public UserServiceImpl (UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void createUser (MLSAuthenticationDto mlsAuthenticationDto) {
    boolean databaseUser = userRepository
      .findByUsernameOrEmail(mlsAuthenticationDto.getUsername(), mlsAuthenticationDto.getEmail())
      .isPresent();

    if (databaseUser) {
      log.error("User already exists");
      throw new IllegalArgumentException("User already exists");
    }

    User user = User
      .builder()
      .articleCount(0)
      .commentCount(0)
      .username(mlsAuthenticationDto.getUsername())
      .nickname(mlsAuthenticationDto.getNickname())
      .email(mlsAuthenticationDto.getEmail())
      .password(passwordEncoder.encode(mlsAuthenticationDto.getPassword()))
      .createTime(LocalDateTime.now())
      .updateTime(LocalDateTime.now())
      .build();
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
    return userRepository
      .findByUsername(username)
      .orElseThrow( () -> new UsernameNotFoundException("User is not found."));
  }

}
