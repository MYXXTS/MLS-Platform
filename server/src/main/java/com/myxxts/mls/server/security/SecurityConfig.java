package com.myxxts.mls.server.security;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.myxxts.mls.server.model.common.HttpResponse;
import com.myxxts.mls.server.model.consts.MLSConst;
import com.myxxts.mls.server.model.entity.User;
import com.myxxts.mls.server.security.entity.MLSAuthenticationVo;
import com.myxxts.mls.server.security.filter.MLSAuthenticationFilter;
import com.myxxts.mls.server.security.filter.SystemStatusCheckFilter;
import com.myxxts.mls.server.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity (debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtUtil jwtUtil;

  private final ObjectMapper objectMapper;

  private final AuthenticationConfiguration authenticationConfiguration;

  private final SystemStatusCheckFilter systemStatusCheckFilter;

  @Bean
  SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
    http
      .httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable)
      .logout(AbstractHttpConfigurer::disable)
      .requestCache(AbstractHttpConfigurer::disable)
      .sessionManagement(
        session -> session
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .securityContext(
        security -> security
          .requireExplicitSave(false)
      )
      .headers(
        headers -> headers
          .frameOptions(frame -> frame.disable())
          .xssProtection(xss -> xss.disable())
          .cacheControl(cache -> cache.disable())
      )
      .authorizeHttpRequests(
        authorize -> authorize
          .requestMatchers(
            MLSConst.PathPrefix.AUTH + "/login",
            MLSConst.PathPrefix.AUTH + "/register"
          )
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .addFilterBefore(systemStatusCheckFilter, UsernamePasswordAuthenticationFilter.class)
      .addFilterAt(mlsAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(
        exception -> exception
          .authenticationEntryPoint(this::onAuthenticationEntryPoint)
          .accessDeniedHandler(this::onAccessDenied)
      );
    return http.build();
  }

  @Bean
  MLSAuthenticationFilter mlsAuthenticationFilter () throws Exception {
    MLSAuthenticationFilter filter = new MLSAuthenticationFilter();
    filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
    filter.setAuthenticationSuccessHandler(this::onAuthenticationSuccess);
    filter.setAuthenticationFailureHandler(this::onAuthenticationFailure);
    return filter;
  }

  // 认证成功处理
  private void onAuthenticationSuccess (
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) throws IOException {
    response.setContentType(MediaType.JSON_UTF_8.toString());
    MLSAuthenticationVo mlsAuthenticationVo = new MLSAuthenticationVo();
    User principal = (User) authentication.getPrincipal();
    BeanUtils.copyProperties(principal, mlsAuthenticationVo);
    mlsAuthenticationVo.setAccessToken(jwtUtil.generateAccessToken(principal, null));
    mlsAuthenticationVo.setRefreshToken(jwtUtil.generateRefreshToken(principal, null));
    HttpResponse<MLSAuthenticationVo> result = HttpResponse.success(mlsAuthenticationVo, "Login Success.");
    response.getWriter().write(objectMapper.writeValueAsString(result));
  }

  // 认证失败处理
  private void onAuthenticationFailure (
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException exception
  ) throws IOException {
    response.setContentType(MediaType.JSON_UTF_8.toString());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    HttpResponse<MLSAuthenticationVo> result = HttpResponse
      .error(HttpServletResponse.SC_UNAUTHORIZED, "Login Failure." + exception.getMessage());
    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
  }

  // 未认证处理
  private void onAuthenticationEntryPoint (
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException exception
  ) throws IOException {
    response.setContentType(MediaType.JSON_UTF_8.toString());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    HttpResponse<MLSAuthenticationVo> result = HttpResponse
      .error(HttpServletResponse.SC_UNAUTHORIZED, "Please Login First.");
    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
  }

  // 访问拒绝处理
  private void onAccessDenied (
    HttpServletRequest request,
    HttpServletResponse response,
    AccessDeniedException exception
  ) throws IOException {
    response.setContentType(MediaType.JSON_UTF_8.toString());
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    HttpResponse<MLSAuthenticationVo> result = HttpResponse
      .error(HttpServletResponse.SC_FORBIDDEN, "Access Denied.");
    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
  }

  @Bean
  PasswordEncoder passwordEncoder () {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
