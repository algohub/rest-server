package org.algohub.rest.service.impl;

import org.algohub.rest.service.SecurityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SecurityServiceImpl implements SecurityService {

  @Override
  public Boolean hasProtectedAccess() {
    final Collection<?> tmp = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(
        new SimpleGrantedAuthority("ADMIN"));
  }
}
