package com.myxxts.mls.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myxxts.mls.server.config.filter.SystemInitializationFilter;

@Configuration
public class SecurityConfig {

  private final SystemInitializationFilter initializeFilter;

  public SecurityConfig (SystemInitializationFilter initializeFilter) {

    this.initializeFilter = initializeFilter;

  }

  @Bean
  protected AuthenticationEntryPoint authenticationEntryPoint () {

    return (req, res, anthException) -> {
      res.setContentType("text/json;charset-utf-8");
      res.getWriter().write(HttpStatus.UNAUTHORIZED.toString());
    };

  }

  @Bean
  SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

    http
      .formLogin(AbstractHttpConfigurer::disable) // 禁用表单登录
      .logout(AbstractHttpConfigurer::disable) // 禁用登出
      .csrf(AbstractHttpConfigurer::disable) // 禁用 CSRF
      .headers(AbstractHttpConfigurer::disable) // 禁用 Security Headers
      .httpBasic(AbstractHttpConfigurer::disable) // 禁用 HTTP Basic认证
      .anonymous(AbstractHttpConfigurer::disable) // 禁用匿名用户
      .servletApi(AbstractHttpConfigurer::disable) // 禁用 ServletApi 集成
      .requestCache(AbstractHttpConfigurer::disable) // 禁用请求缓存
      .securityContext(AbstractHttpConfigurer::disable) // 禁用 SecurityContext 集成
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http
      .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()));

    http.addFilterBefore(initializeFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();

  }

  @Bean
  PasswordEncoder passwordEncoder () {

    return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

  }

}
