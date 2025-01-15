package com.myxxts.mls.server.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.myxxts.mls.server.model.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nonnull;

@Component
public class JwtUtil {

  @Value ("${mls.security.jwt.key}")
  private String key;

  // @Value ("${mls.security.jwt.access-token-expiration}")
  private Long accessTokenExpiration = 12 * 60 * 60 * 1000L;

  // @Value ("${mls.security.jwt.refresh-token-expiration}")
  private Long refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000L;

  /**
   * Generate the access jwt token.
   * 
   * @param user
   * @param additionClaims
   * @return
   */
  public String generateAccessToken (@Nonnull User user, Map<String, Object> additionClaims) {
    return generateToken(accessTokenExpiration, user, additionClaims);
  }

  /**
   * Generate the refresh jwt token.
   * 
   * @param user
   * @param additionClaims
   * @return
   */
  public String generateRefreshToken (@Nonnull User user, Map<String, Object> additionClaims) {
    return generateToken(refreshTokenExpiration, user, additionClaims);
  }

  /**
   * Extract username from jwt token.
   * 
   * @param token
   * @return
   */
  public String extractUsername (String token) {
    return parseToken(token).getSubject();
  }

  /**
   * Generate the jwt token.
   * 
   * @param expMS
   * @param user
   * @param additionClaims
   * @return
   */
  private String generateToken (long expMS, @Nonnull User user, Map<String, Object> additionClaims) {
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    Date now = new Date();
    Date exp = new Date(now.getTime() + expMS);
    JwtBuilder token = Jwts
      .builder();

    if (additionClaims != null) {
      token.claims(additionClaims);
    }

    return token
      .id(user.getUid().toString())
      .issuedAt(now)
      .expiration(exp)
      .subject(user.getUsername())
      .signWith(secretKey)
      .compact();
  }

  /**
   * Parse jwt token.
   * 
   * @param token
   * @return
   */
  private Claims parseToken (String token) {
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    return Jwts
      .parser()
      .verifyWith(secretKey)
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

}
