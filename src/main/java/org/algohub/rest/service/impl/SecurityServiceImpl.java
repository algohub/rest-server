package org.algohub.rest.service.impl;

import org.algohub.rest.service.SecurityService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

  @Override
  public Boolean hasProtectedAccess() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(
        new SimpleGrantedAuthority("ADMIN"));
  }
}
