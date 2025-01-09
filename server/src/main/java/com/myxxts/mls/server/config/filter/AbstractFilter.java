package com.myxxts.mls.server.config.filter;

import java.io.IOException;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myxxts.mls.server.service.SettingService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractFilter extends OncePerRequestFilter {

  protected final AntPathMatcher antPathMatcher;

  protected final SettingService settingService;

  protected final ObjectMapper objectMapper;

  public AbstractFilter (
    SettingService settingService, ObjectMapper objectMapper, AntPathMatcher antPathMatcher
  ) {

    this.antPathMatcher = antPathMatcher;
    this.settingService = settingService;
    this.objectMapper = objectMapper;

  }

  @Override
  protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    // TODO Auto-generated method stub

  }

}
