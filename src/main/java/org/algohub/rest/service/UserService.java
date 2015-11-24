package org.algohub.rest.service;

import org.algohub.rest.domain.User;
import org.algohub.rest.exception.UserAlreadyExistException;
import org.algohub.rest.exception.UserNotExistException;


public interface UserService {
  User create(final User user) throws UserAlreadyExistException;

  User getUserById(long id);

  User getUserByEmail(String email);

  User getUserByName(String name);

  User update(final User user) throws UserNotExistException;

  void delete(final User user);

  boolean exists(final String username);
}
