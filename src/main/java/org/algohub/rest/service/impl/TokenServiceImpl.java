package org.algohub.rest.service.impl;

import org.algohub.rest.security.TokenUtils;
import org.algohub.rest.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenServiceImpl implements TokenService{
  private static final String REVOKED_TOKEN_KEY = "algohub:revoked-token:";
  private static final String REVOKED_USER_KEY = "algohub:revoked-user:";

  @Autowired
  private ValueOperations<String, String> valueOps;

  @Autowired
  private TokenUtils tokenUtils;

  public void revoke(String token) {
    final String key = String.valueOf(token.hashCode());
    final Date deadline = tokenUtils.getExpirationFromToken(token);
    long timeLeft = deadline.getTime() - System.currentTimeMillis();
    if (timeLeft > 0) {
      valueOps.set(REVOKED_TOKEN_KEY + key, "1", timeLeft, TimeUnit.MILLISECONDS);
    }
  }

  // revoke all tokens of the user
  public void revokeAll(String username) {
    valueOps.set(REVOKED_USER_KEY + username, "1", tokenUtils.getExpiration(), TimeUnit.SECONDS);
  }

  public boolean isRevoked(String token) {
    final String key = String.valueOf(token.hashCode());
    final String oneRevoked = valueOps.get(REVOKED_TOKEN_KEY + key);

    final String allRevoked = valueOps.get(REVOKED_USER_KEY +
        tokenUtils.getUsernameFromToken(token));
    return oneRevoked != null || allRevoked != null;
  }

  public boolean validate(String token, UserDetails userDetails) {
    if (isRevoked(token)) return false;

    try {
      final Claims claims = Jwts.parser()
          .setSigningKey(tokenUtils.getSecret())
          .parseClaimsJws(token)
          .getBody();
      final String username = claims.getSubject();
      final Date expiration = claims.getExpiration();
      final String password = claims.get(TokenUtils.PASSWORD_KEY, String.class);

      return username.equals(userDetails.getUsername()) &&
          expiration.after(new Date(System.currentTimeMillis())) &&
          password.equals(userDetails.getPassword().substring(
              userDetails.getPassword().length() - TokenUtils.PASSWORD_LEN));
    } catch (Exception e) {
      return false;
    }
  }

  @Bean
  ValueOperations<String, String> valueOps(StringRedisTemplate redisTemplate) {
    return redisTemplate.opsForValue();
  }
}
