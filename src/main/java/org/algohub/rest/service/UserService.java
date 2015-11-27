package org.algohub.rest.service;

import org.algohub.rest.domain.User;

public interface UserService {
  User create(final User user);

  User getUserById(long id);

  User getUserByEmail(String email);

  User getUserByName(String name);

  User update(final User user);

  User changePassword(String username, String password);

  void delete(final User user);
}
