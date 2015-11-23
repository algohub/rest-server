package org.algohub.rest.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;

import org.algohub.rest.domain.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {
  private static final int PASSWORD_LEN = 8;
  private static final String PASSWORD_KEY = "pas";

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

  public String generateToken(UserDetails userDetails) {
    final Claims claims = new DefaultClaims();
    claims.setSubject(userDetails.getUsername());
    claims.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000));
    claims.put(PASSWORD_KEY, userDetails.getPassword().substring(
        userDetails.getPassword().length() - PASSWORD_LEN));

    return Jwts.builder()
        .setClaims(claims).signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    try {
      final Claims claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
      final String username = claims.getSubject();
      final Date expiration = claims.getExpiration();
      final String password = claims.get(PASSWORD_KEY, String.class);

      return username.equals(userDetails.getUsername()) &&
          expiration.after(new Date(System.currentTimeMillis())) &&
          password.equals(userDetails.getPassword().substring(
              userDetails.getPassword().length() - PASSWORD_LEN));
    } catch (Exception e) {
      return false;
    }
  }
}
