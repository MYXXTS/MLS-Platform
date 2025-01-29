package com.myxxts.mls.server.security.filter;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPatternParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myxxts.mls.server.exception.SystemNotInitializedException;
import com.myxxts.mls.server.model.entity.Setting;
import com.myxxts.mls.server.service.SettingService;

import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SystemStatusCheckFilter extends AbstractBaseSecurityFilter {

  private final SettingService settingService;

  private final Environment environment;

  public SystemStatusCheckFilter (
    PathPatternParser pathPatternParser, ObjectMapper objectMapper, SettingService settingService,
    Environment environment
  ) {
    super(pathPatternParser, objectMapper);
    this.settingService = settingService;
    this.environment = environment;
  }

  @Override
  protected void doFilter (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    HashMap<String, Object> properties = settingService.getSetting(Setting.SYSTEM_STATUS).getProperties();
    boolean initStatus = (boolean) properties.get("Initialized");
    boolean devStatus = Arrays.asList(environment.getActiveProfiles()).indexOf("dev") == -1;

    if (!initStatus && devStatus) {
      handleError(
        response, new SystemNotInitializedException("System is not initialized")
      );
      return;
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected String getToken (HttpServletRequest request) {
    return null;
  }

}
