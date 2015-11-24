package org.algohub.rest.service.impl;

import org.algohub.rest.domain.User;
import org.algohub.rest.exception.UserAlreadyExistException;
import org.algohub.rest.exception.UserNotExistException;
import org.algohub.rest.repository.UserRepository;
import org.algohub.rest.service.UserService;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Caching(put = { @CachePut(cacheNames = "user-id", key = "#user.id"),
      @CachePut(cacheNames = "user-email", key = "#user.email"),
      @CachePut(cacheNames = "user-name", key = "#user.username"),
      @CachePut(cacheNames = "user-exists", key = "#user.username") })
  public @Valid User create(@NotNull @Valid final User user) throws UserAlreadyExistException {
    if (userRepository.exists(user.getId())) {
      throw new UserAlreadyExistException(user.getId());
    } else {
      return userRepository.save(user);
    }
  }


  @Cacheable(cacheNames = "user-id", key = "#id")
  @Transactional(readOnly = true)
  public @Valid User getUserById(@Min(0) long id) {
    return userRepository.findOne(id);
  }

  @Cacheable(cacheNames = "user-email", key = "#email")
  @Transactional(readOnly = true)
  public User getUserByEmail(@Email String email) {
    return userRepository.findOneByEmail(email);
  }

  @Cacheable(cacheNames = "user-name", key = "#username")
  @Transactional(readOnly = true)
  public User getUserByName(@NotNull String username) {
    return userRepository.findOneByUsername(username);
  }

  @Caching(put = { @CachePut(cacheNames = "user-id", key = "#id"),
      @CachePut(cacheNames = "user-email", key = "#email"),
      @CachePut(cacheNames = "user-name", key = "#username") })
  public @Valid User update(@NotNull @Valid final User user) throws UserNotExistException {
    if (userRepository.exists(user.getId())) {
      return userRepository.save(user);
    } else {
      throw new UserNotExistException(user.getId());
    }
  }

  @Caching(evict = { @CacheEvict(cacheNames = "user-id", key = "#user.id"),
      @CacheEvict(cacheNames = "user-email", key = "#user.email"),
      @CacheEvict(cacheNames = "user-name", key = "#user.username"),
      @CacheEvict(cacheNames = "user-exists", key = "#user.username") })
  public void delete(final User user) {
    userRepository.delete(user.getId());
  }

  @Cacheable(cacheNames = "user-exists", key = "#username")
  public boolean exists(final @NotNull String username) {
    final User user = userRepository.findOneByUsername(username);
    return user != null;
  }
}
