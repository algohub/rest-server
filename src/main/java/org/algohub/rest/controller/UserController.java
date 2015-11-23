package org.algohub.rest.controller;

import org.algohub.rest.domain.User;
import org.algohub.rest.exception.UserNotExistException;
import org.algohub.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = RequestMethod.GET, value = "{id}")
  public User get(@PathVariable final long userId) throws UserNotExistException {
    this.validateUser(userId);
    return userService.getUserById(userId);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "{id}")
  public User update(@PathVariable final long userId, @RequestBody @Valid final User user)
      throws UserNotExistException {
    this.validateUser(userId);
    return userService.update(user);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
  public void delete(@PathVariable final long userId) throws UserNotExistException {
    this.validateUser(userId);
    final User user = userService.getUserById(userId);
    userService.delete(user);
  }

  private void validateUser(long userId) throws UserNotExistException {
    final User user = userService.getUserById(userId);
    if (user == null) {
      throw new UserNotExistException(userId);
    }
  }
}
