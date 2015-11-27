package org.algohub.rest.service.impl;

import org.algohub.rest.domain.User;
import org.algohub.rest.repository.UserRepository;
import org.algohub.rest.service.UserService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Caching(put = { @CachePut(cacheNames = "user-email", key = "#user.email"),
      @CachePut(cacheNames = "user-name", key = "#user.username") })
  public @Valid User create(@NotNull @Valid final User user) {
    return userRepository.save(user);
  }


  @Cacheable(cacheNames = "user-id", key = "#id", unless = "#result == null")
  @Transactional(readOnly = true)
  public @Valid User getUserById(@Min(0) long id) {
    return userRepository.findOne(id);
  }

  @Cacheable(cacheNames = "user-email", key = "#email", unless = "#result == null")
  @Transactional(readOnly = true)
  public User getUserByEmail(@Email String email) {
    return userRepository.findOneByEmail(email);
  }

  @Cacheable(cacheNames = "user-name", key = "#username", unless = "#result == null")
  @Transactional(readOnly = true)
  public User getUserByName(@NotNull String username) {
    return userRepository.findOneByUsername(username);
  }

  //TODO: @CachePut(cacheNames = "user-id", key = "#user.id", unless = "#result == null")
  @Caching(put = {
      @CachePut(cacheNames = "user-email", key = "#user.email", unless = "#result == null"),
      @CachePut(cacheNames = "user-name", key = "#user.username", unless = "#result == null") })
  public @Valid User update(@NotNull final User user) {
    final User oldUser = userRepository.findOneByUsername(user.getUsername());
    user.setPasswordHash(oldUser.getPasswordHash());
    //user.setVersion(oldUser.getVersion()+1);
    return userRepository.save(user);
  }

  public @Valid User changePassword(@NotNull final String username, @NotNull final String password) {
    final User user = userRepository.findOneByUsername(username);
    user.setPasswordHash(passwordEncoder.encode(password));
    user.setVersion(user.getVersion()+1);
    return userRepository.save(user);
  }

  @Caching(evict = {
      @CacheEvict(cacheNames = "user-email", key = "#user.email"),
      @CacheEvict(cacheNames = "user-name", key = "#user.username") })
  public void delete(final User user) {
    userRepository.delete(user.getId());
  }
}
