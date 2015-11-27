package org.algohub.rest.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;

import org.algohub.rest.domain.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class TokenUtils {
  public static final int PASSWORD_LEN = 8;
  public static final String PASSWORD_KEY = "pas";

  @Value("${algohub.token.secret}")
  private String secret;
  @Value("${algohub.token.expiration}")
  private long expiration;

  public String getUsernameFromToken(String token) {
    String username;
    try {
      username = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  public Date getExpirationFromToken(String token) {
    Date expiration;
    try {
      expiration = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody()
          .getExpiration();
    } catch (Exception e) {
      expiration = null;
    }
    return expiration;
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(userDetails, this.expiration);
  }

  public String generateToken(UserDetails userDetails, long seconds) {
    return generateToken(userDetails, seconds, null);
  }

  public String generateToken(UserDetails userDetails, long seconds, Map<String, Object> payload) {
    final Claims claims = new DefaultClaims();
    claims.setSubject(userDetails.getUsername());
    claims.setExpiration(new Date(System.currentTimeMillis() + seconds * 1000));
    claims.put(PASSWORD_KEY, userDetails.getPassword().substring(
        userDetails.getPassword().length() - PASSWORD_LEN));

    if (payload != null && !payload.isEmpty()) {
      for (Map.Entry<String, Object> entry : payload.entrySet()) {
        final String key = entry.getKey();
        final Object value = entry.getValue();
        claims.put(key, value);
      }
    }

    return Jwts.builder()
        .setClaims(claims).signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public String getSecret() {
    return secret;
  }

  public long getExpiration() {
    return expiration;
  }
}
