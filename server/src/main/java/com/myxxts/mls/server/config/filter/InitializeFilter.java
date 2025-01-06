package com.myxxts.mls.server.config.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myxxts.mls.server.model.ErrorResponse;
import com.myxxts.mls.server.service.SettingService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InitializeFilter extends OncePerRequestFilter {

  private final SettingService settingService;

  private final ObjectMapper objectMapper;

  public InitializeFilter (SettingService settingService, ObjectMapper objectMapper) {

    this.settingService = settingService;
    this.objectMapper = objectMapper;

  }

  @Override
  protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    try {
      boolean initialized = Boolean.parseBoolean(
        settingService.getProperties("System Config").get("isInitialized")
      );

      if (!initialized) {
        handleError(response, 503, "系统未初始化");
        return;
      }

      filterChain.doFilter(request, response);
    }
    catch (Exception e) {
      handleError(response, 500, e.getMessage());
    }

  }

  private void handleError (HttpServletResponse response, int status, String message) throws IOException {

    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    ErrorResponse errorResponse = ErrorResponse.of(status, message);
    objectMapper.writeValue(response.getOutputStream(), errorResponse);

  }

}
