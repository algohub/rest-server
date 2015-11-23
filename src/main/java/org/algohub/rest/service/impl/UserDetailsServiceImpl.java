package org.algohub.rest.service.impl;

import org.algohub.rest.domain.User;
import org.algohub.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    User user = userRepository.findOneByUsername(name);
    if (user != null) {
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
          user.getPasswordHash(), user.getRoles());
    } else {
      throw new UsernameNotFoundException(name);
    }
  }
}
