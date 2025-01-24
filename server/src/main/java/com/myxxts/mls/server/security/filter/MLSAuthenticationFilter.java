package com.myxxts.mls.server.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myxxts.mls.server.model.consts.MLSConst;
import com.myxxts.mls.server.security.entity.MLSAuthenticationDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MLSAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public MLSAuthenticationFilter () {
    super(new AntPathRequestMatcher(MLSConst.PathPrefix.AUTH + "/login", "POST"));
  }

  @Override
  public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException, IOException, ServletException {

    try {
      // 读取JSON数据
      MLSAuthenticationDto mlsAuthenticationDto = objectMapper
        .readValue(
          request.getInputStream(),
          MLSAuthenticationDto.class
        );

      // 创建未认证的Authentication
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        mlsAuthenticationDto.getUsername(),
        mlsAuthenticationDto.getPassword()
      );

      // 设置详细信息
      authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // 执行认证
      return this.getAuthenticationManager().authenticate(authRequest);

    }
    catch (IOException e) {
      throw new AuthenticationServiceException("Failed to parse authentication request body", e);
    }

  }

}
