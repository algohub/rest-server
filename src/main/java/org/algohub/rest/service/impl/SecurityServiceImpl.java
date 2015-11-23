package org.algohub.rest.service.impl;

import org.algohub.rest.service.SecurityService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SecurityServiceImpl implements SecurityService {

  @Override
  public Boolean hasProtectedAccess() {
    Collection<? extends GrantedAuthority> tmp = SecurityContextHolder.getContext()
        .getAuthentication().getAuthorities();
    final ArrayList<String> authorities = new ArrayList<>();
    for (final GrantedAuthority authority : tmp) {
      authorities.add(authority.getAuthority());
    }
    return authorities.contains("ROLE_ADMIN");
  }
}
