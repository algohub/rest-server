package org.algohub.rest.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
  void revoke(String token);
  boolean isRevoked(String token);
  boolean validate(String token, UserDetails userDetails);
  // when a user changes his password or deletes himself, all of his tokens become invalid
  void revokeAll(String username);
}
