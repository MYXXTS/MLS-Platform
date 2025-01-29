package com.myxxts.mls.server.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.myxxts.mls.server.exception.AbstractMLSBaseException;
import com.myxxts.mls.server.model.common.HttpResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractBaseSecurityFilter extends OncePerRequestFilter {

  protected final PathPatternParser pathPatternParser;

  protected final ObjectMapper objectMapper;

  private Set<String> excludeUrlPatterns = new HashSet<>(32, 0.75F);

  private Set<String> urlPatterns = new LinkedHashSet<>(16, 0.8F);

  /**
   * Get token from request
   * 
   * @param request
   * @return
   */
  protected abstract String getToken (@NonNull HttpServletRequest request);

  /**
   * If the request should be filtered
   * 
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  protected abstract void doFilter (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException;

  @Override
  protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    if (shouldNotFilter(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    doFilter(request, response, filterChain);
  }

  @Override
  protected boolean shouldNotFilter (HttpServletRequest request) throws ServletException {

    if (excludeUrlPatterns.isEmpty() && urlPatterns.isEmpty()) {
      return false;
    }

    boolean exclude = this.excludeUrlPatterns
      .stream()
      .anyMatch(
        pattern -> pathPatternParser
          .parse(pattern)
          .matches(
            PathContainer.parsePath(request.getRequestURI())
          )
      );

    return exclude || this.urlPatterns
      .stream()
      .noneMatch(
        pattern -> pathPatternParser
          .parse(pattern)
          .matches(
            PathContainer.parsePath(request.getRequestURI())
          )
      );
  }

  /**
   * Add exclude url patterns
   * 
   * @param urlPatterns
   */
  public void addExcludeUrlPatterns (String... urlPatterns) {
    Assert.notNull(urlPatterns, "UrlPatterns must not be null");
    Collections.addAll(this.excludeUrlPatterns, urlPatterns);
  }

  /**
   * Get exclude url patterns
   * 
   * @return
   */
  public Collection<String> getExcludeUrlPatterns () {
    return excludeUrlPatterns;
  }

  /**
   * Add url patterns
   * 
   * @param urlPatterns
   */
  public void addUrlPatterns (String... urlPatterns) {
    Assert.notNull(urlPatterns, "UrlPatterns must not be null");
    Collections.addAll(this.urlPatterns, urlPatterns);
  }

  /**
   * Get url patterns
   * 
   * @return
   */
  public Collection<String> getUrlPatterns () {
    return urlPatterns;
  }

  /**
   * Handle error from the filter in every stage
   * 
   * @param response
   * @param status
   * @param message
   * @throws IOException
   */
  protected void handleError (HttpServletResponse response, AbstractMLSBaseException error) throws IOException {
    int value = error.getHttpStatus().value();
    response.setStatus(value);
    response.setCharacterEncoding("UTF-8");
    response.setContentType(MediaType.JSON_UTF_8.toString());
    HttpResponse<?> httpResponse = HttpResponse.error(value, error.getMessage());
    objectMapper.writeValue(response.getOutputStream(), httpResponse);
    log.error(error.getMessage());
  }

}
